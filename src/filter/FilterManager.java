package filter;

import user.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class FilterManager {
    private static Scanner in = new Scanner(System.in);
    public ArrayList<FilterData> filters;

    public FilterManager() {
        filters = new ArrayList<FilterData>();
    }


    public void add(String filterName, float bright, float contrast, float cloudy, float chroma, String madeBy) {
        FilterData filterData = new FilterData(filterName, bright, contrast, cloudy, chroma, madeBy);
        filters.add(filterData);
    }

    public int findLocation(String filterName) {
        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i).getFilterName().equals(filterName)) {
                return i;
            }
        }
		System.out.println("해당하는 이름의 필터가 존재하지 않습니다.");
        return -1;
    }

    public void remove(int pos) {
        filters.remove(pos);
    }

    public FilterData get(int pos) {
        return filters.get(pos);
    }

    // 모든 필터 보기
    public void showAllData() {
        for(FilterData filter : filters){
            filter.printInfo();
        }
    }

    // 필터 추가
    public void addFilter() {
        while (true) {
            System.out.print("filtername (quit to exit) : ");
            String filterName = in.next();
            if (filterName.equals("quit")) break;
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
            filters.add(new FilterData(filterName, bright, contrast, cloudy, chroma, curUserNickname));

        }
    }

    // 필터 업데이트하기
    public void updateFilter() {
        System.out.print("Filter name to update : ");
        String filterName = in.next();
        int pos = findLocation(filterName);
        if (pos != -1) {
            System.out.print("New name : ");
            filterName = in.next();
            System.out.print("New bright : ");
            float bright = in.nextFloat();
            System.out.print("New contrast : ");
            float contrast = in.nextFloat();
            System.out.print("New cloudy : ");
            float cloudy = in.nextFloat();
            System.out.print("New chroma : ");
            float chroma = in.nextFloat();
            // 현재 로그인 되어있는 유저의 닉네임
            String curUserNickname = UserManager.loggedInUser.getNickName();
            filters.set(pos, new FilterData(filterName, bright, contrast, cloudy, chroma, curUserNickname));
        }
    }

    // 필터 제거하기
    public void removeFilter() {
        System.out.print("Filter name to remove : ");
        String filterName = in.next();
        int pos = findLocation(filterName);
        if (pos != -1) {
            remove(pos);
        } else {
            System.out.println("Can't find filter!");
        }

    }

}
