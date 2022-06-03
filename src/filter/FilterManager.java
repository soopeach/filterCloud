package filter;

import java.util.ArrayList;

public class FilterManager {
    public ArrayList<FilterData> filters;

    public FilterManager() {
        filters = new ArrayList<FilterData>();
    }


    public void add(String filterName, float bright, float contrast, float cloudy, float chroma, String madeBy) {
        FilterData filterData = new FilterData(filterName, bright, contrast, cloudy, chroma, madeBy);
        filters.add(filterData);
    }

    public void showAllData() {
		for(FilterData filter : filters){
			filter.printInfo();
		}
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

}
