package filter;

public class PhotoFilter extends FilterData{

    private String property = "사진";

    public PhotoFilter(String filterName, float bright, float contrast, float cloudy, float chroma, String madeBy) {
        super(filterName, bright, contrast, cloudy, chroma, madeBy);
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "속성 ='" + property + '\'' + ']';
    }
}
