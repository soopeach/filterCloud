package user;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class UserManager {
    private static Scanner in = new Scanner(System.in);
    public static ArrayList<User> userList = new ArrayList<>();
    public static User loggedInUser = new User("notYet","notYet","notYet");
    public static Boolean isLogin = false;

    // 객체 생성과 동시에 유저 리스트를 불러오기.
    public UserManager() {
        // 유저 데이터가 저장되어있는 userDataList.csv를 불러와서 읽은 후 userList에 저장함.
        // 데이터를 불러오는 작업은 오랜시간을 소요할 수 있어 별도의 스레드에서 동작.
        // Runnable 인터페이스를 람디식으로 구현함.
        Runnable loadUserList = () -> {
            loadUserList("UserDataList.csv");
        };
        Thread threadForLoadUserList = new Thread(loadUserList);
        threadForLoadUserList.start();
    }

    // 유저 데이터 목록 파일 읽어오기.
    public static void loadUserList(String fileName){
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
    public static void saveUserList(){
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

    // 로그인
    public void login(){
        while (!UserManager.isLogin){
            System.out.println("아이디를 입력해주세요. : ");
            String id = in.next();
            System.out.println("비밀번호를 입력해주세요. : \n뒤로 가러면 quit을 입력해주세요.");
            String password = in.next();

            if (password.equals("quit")) break;

            // 데이터에 있는 id, password인지 확인. 동일하다면 로그인처리
            for(User user : UserManager.userList){
                if (user.getId().equals(id) && user.getPassword().equals(password)){
                    UserManager.loggedInUser.setId(user.getId());
                    UserManager.loggedInUser.setPassword(user.getPassword());
                    UserManager.loggedInUser.setNickName(user.getNickName());
                    System.out.println("로그인에 성공하였습니다. 반갑습니다. " + UserManager.loggedInUser.getNickName() + "님! 오늘도 좋은 하루되세요~!");
                    UserManager.isLogin = true;
                    break;
                }
            }
            if (!UserManager.isLogin) System.out.println("없는 정보입니다. 다시 입력해주세요. ");
        }

    }

    // 회원가입
    public void signUp() {
        Boolean signUpSuccess;
        while(!UserManager.isLogin){
            signUpSuccess = true;
            System.out.println("회원가입할 아이디를 입력해주세요. : ");
            String id = in.next();
            System.out.println("회원가입할 비밀번호를 입력해주세요. : ");
            String password = in.next();
            System.out.println("닉네임을를 입력해주세요. : ");
            String nickName = in.next();

            // id 중복여부 검사.
            for(User user : UserManager.userList){
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
                UserManager.loggedInUser.setId(newUser.getId());
                UserManager.loggedInUser.setPassword(newUser.getPassword());
                UserManager.loggedInUser.setNickName(newUser.getNickName());

                // UserDataList에 갱신하기 위함.
                UserManager.userList.add(newUser);
                UserManager.isLogin = true;
                System.out.println("회원가입이 완료되었습니다. 반갑습니다. " + UserManager.loggedInUser.getNickName() + "님 오늘도 좋은 하루되세요~!");
            }

        }
        // 새로 회원가입한 유저의 정보를 UserDataList에 추가.
        UserManager.saveUserList();
    }
}
