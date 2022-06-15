package filter;

public class NormalFilter extends FilterData{

    private String degreeOfBrightness = "보통";

    public NormalFilter(String filterName, float bright, float contrast, float cloudy, float chroma, String madeBy, String degreeOfBrightness) {
        super(filterName, bright, contrast, cloudy, chroma, madeBy);
        this.degreeOfBrightness = degreeOfBrightness;
    }
}
