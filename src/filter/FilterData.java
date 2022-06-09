package filter;

public class FilterData {
    private String filterName;
    private float bright, contrast, cloudy, chroma;
    private String madeBy;


    public FilterData(String filterName, float bright, float contrast, float cloudy, float chroma, String madeBy) {
        this.filterName = filterName;
        this.bright = bright;
        this.contrast = contrast;
        this.cloudy = cloudy;
        this.chroma = chroma;
        this.madeBy = madeBy;
    }

    public float getBright() {
        return bright;
    }

    public FilterData setBright(float bright) {
        this.bright = bright;
        return this;
    }

    public float getContrast() {
        return contrast;
    }

    public FilterData setContrast(float contrast) {
        this.contrast = contrast;
        return this;
    }

    public float getCloudy() {
        return cloudy;
    }

    public FilterData setCloudy(float cloudy) {
        this.cloudy = cloudy;
        return this;
    }

    public float getChroma() {
        return chroma;
    }

    public FilterData setChroma(float chroma) {
        this.chroma = chroma;
        return this;
    }

    public String getFilterName() {
        return filterName;
    }

    public FilterData setFilterName(String filterName) {
        this.filterName = filterName;
        return this;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    @Override
    public String toString() {
        return "filter.FilterData [filterName=" + filterName + ", bright=" + bright + ", contrast=" + contrast + ", cloudy="
                + cloudy + ", chroma=" + chroma + "]";
    }

    public void printInfo() {
        System.out.println("필터 이름 : " + filterName);
        System.out.println("밝기 : " + bright);
        System.out.println("대비 : " + contrast);
        System.out.println("흐림 : " + cloudy);
        System.out.println("채도 : " + chroma);
        System.out.println("만든이 : " + madeBy);
    }

}
