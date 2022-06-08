import filter.FilterData;
import filter.FilterManager;
import user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    private static Scanner in = new Scanner(System.in);
    public static FilterManager manager = new FilterManager();
    static ArrayList<User> userList = new ArrayList<>();
    static User loggedInUser = new User("notYet","notYet","notYet");
    static Boolean isLogin = false;

    // 유저 데이터 목록 파일 읽어오기.
    private static void loadUserList(String fileName){
        try {
            FileReader reader = new FileReader(fileName); // 파일열기. 파일이 없으면 FileNotFoundException 오류 발생.
            BufferedReader buf = new BufferedReader(reader);
            String line;
            while((line = buf.readLine()) != null){
                StringTokenizer tokenizer = new StringTokenizer(line, "#");
                String id = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                String nickName = tokenizer.nextToken();

                userList.add(new User(id,password,nickName));
            }
            buf.close();
        } catch (FileNotFoundException e) {
            System.out.println(fileName + " 파일이 존재하지 않습니다.");
            // e.printStackTrace();
        } catch (IOException e) {
            System.out.println(fileName + " 파일로부터 데이터 읽기 중에 오류가 발생했습니다.");
            throw new RuntimeException(e);
        }
    }

    // 유저 데이터 목록 파일 갱신하기.
    private static void saveUserList(ArrayList<User> userList){
        // 파일열기, 기록하기, 파일 닫기
        // try ~ catch(exception) : 예외처리구문. try 블록에서 예외(오류)가 발생하면 오류 종류에 따라 매칭되는 catch 구문이 실행됨.
        try {

            FileWriter writer = new FileWriter("UserDataList.csv"); // 유저의 데이터(is, password, nickName)가 담겨있는 파일.
            BufferedWriter buf = new BufferedWriter(writer);

            // 파일에 데이터 기록하기
            for(User user : userList){
                buf.write(user.getId() + "#");
                buf.write(user.getPassword() + "#");
                buf.write(user.getNickName()); // double --> string
                buf.newLine(); // 개행 (다음 줄로 내림)
            }
            // 파일 닫기
            buf.close();
            System.out.println("데이터 저장이 완료되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 메뉴 보여주기
    private static void menu() {
        System.out.println("[[필터 클라우드 서비스]]");
        System.out.println("1. 내 필터 모두 보기");
        System.out.println("2. 필터 추가하기");
        System.out.println("3. 필터 수정하기");
        System.out.println("4. 필터 삭제하기");
        System.out.println("0. 종료");
    }


    public static void main(String[] args) {

        // 유저 데이터가 저장되어있는 userDataList.csv를 불러와서 읽은 후 userList에 저장함.
        loadUserList("UserDataList.csv");

        // 로그인을 한 상태가 아니라면 로그인 먼저.
        while (!isLogin){
            // 유저 로그인 기능.
            System.out.println("[로그인을 해주세요. 아이디가 없다면 회원가입을 해주세요.]");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");

            int command = in.nextInt();

            // 로그인
            if (command == 1){
                while (!isLogin){
                    System.out.println("아이디를 입력해주세요. : ");
                    String id = in.next();
                    System.out.println("비밀번호를 입력해주세요. : \n뒤로 가러면 quit을 입력해주세요.");
                    String password = in.next();

                    if (password.equals("quit")) break;

                    // 데이터에 있는 id, password인지 확인. 동일하다면 로그인처리
                    for(User user : userList){
                        if (user.getId().equals(id) && user.getPassword().equals(password)){
                            loggedInUser.setId(user.getId());
                            loggedInUser.setPassword(user.getPassword());
                            loggedInUser.setNickName(user.getNickName());
                            System.out.println("로그인에 성공하였습니다. 반갑습니다. " + loggedInUser.getNickName() + "님! 오늘도 좋은 하루되세요~!");
                            isLogin = true;
                            break;
                        }
                    }
                    if (!isLogin) System.out.println("없는 정보입니다. 다시 입력해주세요. ");
                }

            // 회원가입
            } else if (command == 2){
                Boolean signUpSuccess;
                while(!isLogin){
                    signUpSuccess = true;
                    System.out.println("회원가입할 아이디를 입력해주세요. : ");
                    String id = in.next();
                    System.out.println("회원가입할 비밀번호를 입력해주세요. : ");
                    String password = in.next();
                    System.out.println("닉네임을를 입력해주세요. : ");
                    String nickName = in.next();

                    // id 중복여부 검사.
                    for(User user : userList){
                        if (user.getId().equals(id)){
                            System.out.println("중복된 아이디 입니다. 다시 입력해주세요.");
                            signUpSuccess = false;
                            break;
                        }
                    }

                    // 중복되지 않았을 때만 회원가입에 성공
                    if (signUpSuccess){
                        User newUser = new User(id, password, nickName);
                        // 회원가입 후 자동 로그인 처리
                        loggedInUser.setId(newUser.getId());
                        loggedInUser.setPassword(newUser.getPassword());
                        loggedInUser.setNickName(newUser.getNickName());

                        // UserDataList에 갱신하기 위함.
                        userList.add(newUser);
                        isLogin = true;
                        System.out.println("회원가입이 완료되었습니다. 반갑습니다. " + loggedInUser.getNickName() + "님 오늘도 좋은 하루되세요~!");
                    }

                }
                // 새로 회원가입한 유저의 정보를 UserDataList에 추가.
                saveUserList(userList);

            } else {
                System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
            }

        }

        // 필터 관리 기능.
        while (true) {
            menu();
            int menu = in.nextInt();
            if (menu == 0) {
                System.out.println("시스템을 종료합니다.");
                break;
            }
            switch (menu) {
                // 모든 필터 보기.
                case 1 -> manager.showAllData();
                // 필터 추가하기.
                case 2 -> manager.addFilter();
                // 필터 업데이트하기.
                case 3 -> manager.updateFilter();
                // 필터 삭제하기.
                case 4 -> manager.removeFilter();

                default -> System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
            }
        }

    }
}
