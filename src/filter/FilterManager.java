package filter;

import user.User;
import user.UserManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FilterManager {
    private static Scanner in = new Scanner(System.in);
    private static ArrayList<FilterData> filterList;
    private Random random = new Random();

    public FilterManager() {

        filterList = new ArrayList<>();

        // 필터 클라우드에 있는 데이터들을 불러옴.
        // 데이터를 불러오는 작업은 오랜시간을 소요할 수 있어 별도의 스레드에서 동작.
        // Runnable 인터페이스를 람디식으로 구현함.
        Runnable loadFilterCloudData = () -> {
            loadFilterCloud("FilterCloud.csv");
        };
        Thread threadForFilterCloud = new Thread(loadFilterCloudData);
        threadForFilterCloud.start();
    }

    public void add(String filterName, float bright, float contrast, float cloudy, float chroma, String madeBy) {
        FilterData filterData = new FilterData(filterName, bright, contrast, cloudy, chroma, madeBy);
        filterList.add(filterData);
    }

    // 찾고자하는 필터의 이름을 입력받아 내가 만든 필터를 찾아 위치를 반환해줌. 없으면 -1 반환
    public int findMineLocation(String filterName){
        for (int i = 0; i < filterList.size(); i++) {
            FilterData curFilter =  filterList.get(i);
            // 찾고자하는 필터의 이름이 일치하고 해당 필터가 내가 만든 것일 경우에만 위치 반환
            if (curFilter.getFilterName().equals(filterName) && curFilter.getMadeBy().equals(UserManager.loggedInUser.getNickName())) {
                return i;
            }
        }
        return -1;
    }

    public void remove(int pos) {
        filterList.remove(pos);
    }

    // 클라우드에 있는 모든 필터 보기
    public void showAllMine() {
        // 필터 클라우드가 비어있다면 경고문 출력 후 즉시 종료
        if (isFilterCloudEmpty()) return;
        // 내가 만든 필터가 있는지 확인할 변수
        Boolean haveMineFilter = false;
        for (FilterData filter: filterList) {

            // 내가 만든 필터들만 출력.
            if (filter.getMadeBy().equals( UserManager.loggedInUser.getNickName())){
                haveMineFilter = true;
                filter.printInfo();
            }
        }
        // 내가 만든 필터가 존재하지 않는다면
        if(!haveMineFilter) System.out.println(UserManager.loggedInUser.getNickName() + "님이 만드신 필터가 존재하지 않습니다.");
    }

    // 필터 추가
    public void addFilter() {
        while (true) {
            System.out.print("필터의 이름을 입력해주세요. (나가시려면 종료를 입력해주세요.) : ");
            String filterName = in.next();
            if (filterName.equals("종료")) break;
            System.out.print("bright : ");
            float bright = in.nextFloat();
            System.out.print("contrast : ");
            float contrast = in.nextFloat();
            System.out.print("cloudy : ");
            float cloudy = in.nextFloat();
            System.out.print("chroma : ");
            float chroma = in.nextFloat();
            // 현재 로그인 되어있는 유저의 닉네임
            String curUserNickname = UserManager.loggedInUser.getNickName();
            filterList.add(new FilterData(filterName, bright, contrast, cloudy, chroma, curUserNickname));

        }
        saveFilterCloud();
    }

    // 필터 업데이트하기
    public void updateFilter() {
        // 필터 클라우드가 비어있다면 경고문 출력 후 즉시 종료
        if (isFilterCloudEmpty()) return;
        System.out.print("업데이트를 진행할 필터의 이름을 입력해주세요. : ");
        String filterName = in.next();
        int pos = findMineLocation(filterName);
        if (pos != -1) {
            System.out.print("새로운 name을 입력해주세요. : ");
            filterName = in.next();
            System.out.print("새로운 bright를 입력해주세요. : ");
            float bright = in.nextFloat();
            System.out.print("새로운 contrast를 입력해주세요. : ");
            float contrast = in.nextFloat();
            System.out.print("새로운 cloudy를 입력해주세요. : ");
            float cloudy = in.nextFloat();
            System.out.print("새로운 chroma를 입력해주세요. : ");
            float chroma = in.nextFloat();
            // 현재 로그인 되어있는 유저의 닉네임
            String curUserNickname = UserManager.loggedInUser.getNickName();
            filterList.set(pos, new FilterData(filterName, bright, contrast, cloudy, chroma, curUserNickname));
        } else {
            System.out.print("해당하는 필터가 존재하지 않습니다. ");
        }
        saveFilterCloud();

    }

    // 필터 제거하기
    public void removeFilter() {
        // 필터 클라우드가 비어있다면 경고문 출력 후 즉시 종료
        if (isFilterCloudEmpty()) return;
        System.out.print("삭제할 필터의 이름을 입력해주세요. : ");
        String filterName = in.next();
        int pos = findMineLocation(filterName);

        // 삭제할 필터의 이름과 일치하는 필터가 존재하고, 내가 만든 필터라면 삭제
        if (pos != -1) {
            remove(pos);
            System.out.println("정상적으로 삭제되었습니다..");
        }else {
            System.out.println("해당하는 이름의 필터가 존재하지 않습니다.");
        }

        saveFilterCloud();

    }

    // 클라우드에 있는 모든 필터 보기
    public void showAllData() {
        // 필터 클라우드가 비어있다면 경고문 출력 후 즉시 종료
        if (isFilterCloudEmpty()) return;
        for (FilterData filter : filterList) {
            filter.printInfo();
        }
    }

    // 필터 클라우드 파일 읽어오기.
    public void loadFilterCloud(String fileName) {
        try {
            FileReader reader = new FileReader(fileName); // 파일열기. 파일이 없으면 FileNotFoundException 오류 발생.
            BufferedReader buf = new BufferedReader(reader);
            String line;
            while ((line = buf.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, "#");
                String filterName = tokenizer.nextToken();
                Float bright = Float.parseFloat(tokenizer.nextToken());
                Float contrast = Float.parseFloat(tokenizer.nextToken());
                Float cloudy = Float.parseFloat(tokenizer.nextToken());
                Float chroma = Float.parseFloat(tokenizer.nextToken());
                String madeBy = tokenizer.nextToken();

                filterList.add(new FilterData(filterName, bright, contrast, cloudy, chroma, madeBy));
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

    // 필터 클라우드 파일 갱신하기.
    public static void saveFilterCloud() {
        // 파일열기, 기록하기, 파일 닫기
        // try ~ catch(exception) : 예외처리구문. try 블록에서 예외(오류)가 발생하면 오류 종류에 따라 매칭되는 catch 구문이 실행됨.
        try {
            FileWriter writer = new FileWriter("FilterCloud.csv"); // 필터 클라우드
            BufferedWriter buf = new BufferedWriter(writer);

            // 파일에 데이터 기록하기
            for (FilterData filter : filterList) {
                buf.write(filter.getFilterName() + "#");
                buf.write(filter.getBright() + "#");
                buf.write(filter.getContrast() + "#");
                buf.write(filter.getCloudy() + "#");
                buf.write(filter.getChroma() + "#");
                buf.write(filter.getMadeBy() + "#");
                buf.newLine(); // 개행 (다음 줄로 내림)
            }
            // 파일 닫기
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 랜덤 필터 추천받기
    public void getRandomFilter() {
        // 현재 클라우드에 저장되어있는 필터의 개수
        // 필터 클라우드가 비어있다면 경고문 출력 후 즉시 종료
        if (isFilterCloudEmpty()) return;

        // 클라우드에 있는 필터의 개수
        int cntOfFilter = filterList.size();
        System.out.println("추천하는 필터의 정보입니다~");
        // 랜덤한 필터의 정보를 출력
        int randPos = random.nextInt(cntOfFilter);
        filterList.get(randPos).printInfo();

    }

    // 필터클라우드에 저장되어있는 데이터가 없다면
    // 경고문 출력 후 함수 즉시 종료
    Boolean isFilterCloudEmpty(){
        if (filterList.isEmpty()) {
            System.out.println("클라우드 내부에 필터가 존재하지 않습니다.");
            return true;
        }
        return false;
    }
}
