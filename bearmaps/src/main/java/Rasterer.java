
/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    /** The max image depth level. */
    public static final int MAX_DEPTH = 7;

    /**
     * Takes a user query and finds the grid of images that best matches the query. These images
     * will be combined into one big image (rastered) by the front end. The grid of images must obey
     * the following properties, where image in the grid is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel (LonDPP)
     *     possible, while still covering less than or equal to the amount of longitudinal distance
     *     per pixel in the query box for the user viewport size.</li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the above
     *     condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     * @param params The RasterRequestParams containing coordinates of the query box and the browser
     *               viewport width and height.
     * @return A valid RasterResultParams containing the computed results.
     */

    public RasterResultParams getMapRaster(RasterRequestParams params) {

        /*
         * Hint: Define additional classes to make it easier to pass around multiple values, and
         * define additional methods to make it easier to test and reason about code. */
        double getLonDPP = lonDPP(params.lrlon, params.ullon, params.w);
        int depth = depth(getLonDPP, 0, MapServer.ROOT_LONDPP);
        RasterResultParams.Builder x = new RasterResultParams.Builder();
        if (depth > 7) {
            depth = 7;
        }

        double[] uLLon = ullonHelper(depth, params);
        double[] uLLat = ullatHelper(depth, params);
        double[] lRLon = lrlonHelper(depth, params);
        double[] lrLat = lrlatHelper(depth, params);


        x.setDepth(depth);
        x.setRasterUlLon(uLLon[0]);
        x.setRasterUlLat(uLLat[0]);
        x.setRasterLrLon(lRLon[0]);
        x.setRasterLrLat(lrLat[0]);


        int[] lrImage = new int[] {(int) lRLon[1], (int) lrLat[1]};
        int[] ulImage = new int[]{(int) uLLon[1], (int) uLLat[1]};

        String[][] renderGrid = new String[lrImage[1] - ulImage[1] + 1][lrImage[0]
                - ulImage[0] + 1];
        for (int j = ulImage[1]; j < lrImage[1] + 1; j++) {
            for (int i = ulImage[0]; i < lrImage[0] + 1; i++) {
                renderGrid[j - ulImage[1]][i - ulImage[0]] = "d" + depth + "_x" + i
                        + "_y" + j + ".png";
            }
        }


        x.setQuerySuccess(true);
        x.setRenderGrid(renderGrid);

        RasterResultParams answer = x.create();
        return answer;
    }

    public double[] ullonHelper(int depth, RasterRequestParams params) {
        double increment = MapServer.ROOT_LON_DELTA / Math.pow(2, depth);
        double[] value = new double[2];
        value[1] =  Math.floor((params.ullon - MapServer.ROOT_ULLON) / increment);
        value[0] = value[1] * increment + MapServer.ROOT_ULLON;
        return value;
    }

    public double[] ullatHelper(int depth, RasterRequestParams params) {
        double increment = MapServer.ROOT_LAT_DELTA / Math.pow(2, depth);
        double[] value = new double[2];
        value[1] = Math.floor((MapServer.ROOT_ULLAT - params.ullat) / increment);
        value[0] = MapServer.ROOT_ULLAT - value[1] * increment;
        return value;
    }

    public double[] lrlonHelper(int depth, RasterRequestParams params) {
        double increment = MapServer.ROOT_LON_DELTA / Math.pow(2, depth);
        double[] value = new double[2];
        value[1] = Math.floor((params.lrlon - MapServer.ROOT_ULLON) / increment);
        value[0] =  MapServer.ROOT_ULLON + value[1] * increment;
        if (value[0] < params.lrlon) {
            value[0] += increment;
        }
        return value;
    }

    public double[] lrlatHelper(int depth, RasterRequestParams params) {
        double increment = MapServer.ROOT_LAT_DELTA / Math.pow(2, depth);
        double[] value = new double[2];
        value[1] = Math.floor((MapServer.ROOT_ULLAT - params.lrlat) / increment);
        value[0] = MapServer.ROOT_ULLAT - value[1] * increment;
        if (value[0] > params.lrlat) {
            value[0] -= increment;
        }
        return value;
    }

    public int depth(double reqLonDPP, int depth, double resultingLonDPP) {
        if (reqLonDPP >= resultingLonDPP) {
            return depth;
        }
        return depth(reqLonDPP, depth + 1, resultingLonDPP / 2);
    }

    /**
     * Calculates the lonDPP of an image or query box
     * @param lrlon Lower right longitudinal value of the image or query box
     * @param ullon Upper left longitudinal value of the image or query box
     * @param width Width of the query box or image
     * @return lonDPP
     */
    private double lonDPP(double lrlon, double ullon, double width) {
        return (lrlon - ullon) / width;
    }
}
