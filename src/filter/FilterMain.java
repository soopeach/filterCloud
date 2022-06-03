package filter;

import java.util.Scanner;

public class FilterMain {
//    private static Scanner in = new Scanner(System.in);
//    public static FilterManager manager = new FilterManager();
//
//    // 메뉴 보여주기
//    private static void menu() {
//        System.out.println("[[필터 클라우드 서비스]]");
//        System.out.println("1. 내 필터 모두 보기");
//        System.out.println("2. 필터 추가하기");
//        System.out.println("3. 필터 수정하기");
//        System.out.println("4. 필터 삭제하기");
//        System.out.println("0. 종료");
//    }
//
//    // 필터 추가
//    private static void addFilter() {
//        while (true) {
//            System.out.print("filtername (quit to exit) : ");
//            String filterName = in.next();
//            if (filterName.equals("quit")) break;
//            System.out.print("bright : ");
//            float bright = in.nextFloat();
//            System.out.print("contrast : ");
//            float contrast = in.nextFloat();
//            System.out.print("cloudy : ");
//            float cloudy = in.nextFloat();
//            System.out.print("chroma : ");
//            float chroma = in.nextFloat();
//            FilterData filterData = new FilterData(filterName, bright, contrast, cloudy, chroma);
//            manager.filters.add(filterData);
//
//        }
//    }
//
//    // 모든 필터 보기
//    private static void showAllFilter() {
//        manager.showAllData();
//    }
//
//    // 필터 업데이트하기
//    private static void updateFilter() {
//        System.out.print("Filter name to update : ");
//        String filterName = in.next();
//        int pos = manager.findLocation(filterName);
//        if (pos != -1) {
//            System.out.print("New name : ");
//            filterName = in.next();
//            System.out.print("New bright : ");
//            float bright = in.nextFloat();
//            System.out.print("New contrast : ");
//            float contrast = in.nextFloat();
//            System.out.print("New cloudy : ");
//            float cloudy = in.nextFloat();
//            System.out.print("New chroma : ");
//            float chroma = in.nextFloat();
//            manager.filters.set(pos, new FilterData(filterName, bright, contrast, cloudy, chroma));
//        }
//    }
//
//    private static void removeFilter() {
//        System.out.print("Filter name to remove : ");
//        String filterName = in.next();
//        int pos = manager.findLocation(filterName);
//        if (pos != -1) {
//            manager.remove(pos);
//        } else {
//            System.out.println("Can't find filter!");
//        }
//
//    }
//
//    public static void main(String[] args) {
//        while (true) {
//
//            menu();
//
//            int menu = in.nextInt();
//            if (menu == 0) {
//                System.out.println("시스템을 종료합니다.");
//                break;
//            }
//            switch (menu) {
//                case 1 -> showAllFilter();
//
//                case 2 -> addFilter();
//
//                case 3 -> updateFilter();
//
//                case 4 -> removeFilter();
//            }
//        }
//
//    }


}
