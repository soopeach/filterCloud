package filter;

public class VideoFilter extends FilterData{

    private String property = "비디오";

    public VideoFilter(String filterName, float bright, float contrast, float cloudy, float chroma, String madeBy) {
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
