package com.example.BriqueCSVTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

@SpringBootApplication
public class BriqueCsvTestApplication {


	public static void main(String[] args) {
		int cnt = 0;

		Scanner scanner = new Scanner(System.in);
		System.out.print("CSV 파일이 존재하는 폴더의 '절대경로'를 입력해주세요 (Mac OS ex./Users/사용자 이름/Downloads/sample.csv) : ");
		String folderRoot = scanner.next();
		System.out.println();
		List<List<String>> list = readCSVFile(folderRoot);
		List<String> strList = new ArrayList<>();
		List<Integer> intList = new ArrayList<>();

		for (List<String> item : list) {

			int size = item.size();
			boolean strcheck = false;

			for (String s : item) {
				String pattern = "^[0-9]*$"; //숫자만
				boolean regix = Pattern.matches(pattern, s);
				if (!regix) {
					strList.add(s);
					strcheck = true;
				}

				if (!strcheck) {
					intList.add(Integer.parseInt(s));
				}
			}
			if (!intList.isEmpty()) {
				int max = Collections.max(intList);
				int min = Collections.min(intList);
				int sum = 0;

				for (int i : intList) {
					sum += i;
				}

				int avg = sum / intList.size();
				int middleValueIndex = intList.size() / 2;
				int middleValue = intList.get(middleValueIndex);
				int double_sum = 0;

				//표준편차 구하기
				for (int i : intList) {
					int elementMinusAvg_double = (i - avg) * (i - avg);
					double_sum += elementMinusAvg_double;
				}
				double valriance = 0;
				if (intList.size() != 1) {
					valriance = double_sum / (intList.size() - 1);
				}
				double standard_deviation = Math.sqrt(valriance);
				System.out.println("최솟값 : "+min+", 최대값 :  "+max+", 합계 :  "+sum+", 평균값: : "+avg+", 표준편차 : "+standard_deviation+", 중간값 :  "+middleValue);
				intList.clear();
				cnt++;
			}

		}
		System.out.print("숫자가 아닌 값들 : ");
		for (String s : strList) {
			System.out.print(s+",");
		}
		System.out.println();
		System.out.println("끝"+cnt);
	}

	private static List<List<String>> readCSVFile(String filePath) {

		List<List<String>> list = new ArrayList<List<String>>();
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = Files.newBufferedReader(Paths.get(filePath));
			String line = "";

			while ((line = bufferedReader.readLine()) != null) {

				List<String> stringList = new ArrayList<>();
				String stringArray[] = line.split(",");

				stringList = Arrays.asList(stringArray);
				list.add(stringList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				assert bufferedReader != null;
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
}
