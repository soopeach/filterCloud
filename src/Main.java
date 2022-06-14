import filter.FilterManager;
import user.User;
import user.UserManager;

import java.util.Scanner;

public class Main {

    private static Scanner in = new Scanner(System.in);
    // 필터매니저
    // 객체 생성과 동시에(필터 클라우드의 데이터를 불러오게됨)
    public static FilterManager filterManager = new FilterManager();
    // 유저매니저
    // 객체 생성과 동시에(유저들 데이터를 불러오게됨)
    public static UserManager userManager = new UserManager();

    // 메뉴 보여주기
    private static void menuForFilter() {
        System.out.println("[[필터 클라우드 서비스]]");
        System.out.println("1. 내 필터 모두 보기");
        System.out.println("2. 필터 업로드하기");
        System.out.println("3. 필터 수정하기");
        System.out.println("4. 필터 삭제하기");
        System.out.println("5. 클라우드에 있는 필터 모두 보기");
        System.out.println("6. 랜덤한 필터 추천받기");
        System.out.println("9. 회원탈퇴");
        System.out.println("0. 종료");
    }

    // 로그인 메뉴 보여주기
    private static void menuForLogin() {
        System.out.println("[로그인을 해주세요. 아이디가 없다면 회원가입을 해주세요.]");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("0. 종료");
    }

    public static void main(String[] args) {

        // 로그인을 한 상태가 아니라면 로그인 먼저.
        while (!UserManager.isLogin) {
            menuForLogin();
            int command = in.nextInt();
            // 로그인
            if (command == 1) {
                userManager.login();
            // 회원가입
            } else if (command == 2) {
                userManager.signUp();
            }  else if (command == 0) {
                System.out.println("시스템을 종료합니다.");
                return;
            } else {
                System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
            }
        }

        // 필터 관리 기능.
        while (true) {
            menuForFilter();
            int menu = in.nextInt();
            if (menu == 0) {
                System.out.println("시스템을 종료합니다.");
                break;
            }
            switch (menu) {
                // 내가 올린 모든 필터 보기.
                case 1 -> filterManager.showAllMine();
                // 필터 추가하기.
                case 2 -> filterManager.addFilter();
                // 필터 업데이트하기.
                case 3 -> filterManager.updateFilter();
                // 필터 삭제하기.
                case 4 -> filterManager.removeFilter();
                // 클라우드에 있는 필터 모두보기(타인이 올린 필터포함)
                case 5 -> filterManager.showAllData();
                // 랜덤한 필터 추천받기
                case 6 -> filterManager.getRandomFilter();

                // 회원탈퇴
                case 9 -> {
                    userManager.signOut();
                    System.out.println("이용해주셔서 감사합니다.");
                    return;
                }

               default -> System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
            }
        }

    }
}
