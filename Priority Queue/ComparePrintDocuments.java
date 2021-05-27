public class ComparePrintDocuments implements Comparator<PrintDocument> {
    /**Weight factor for size */
    private static final double P1=0.8;
    /**Weight factor for time */
    private static final double P2=0.2;

    /**
     * Compare two printdocuments
     */
    public int compare(PrintDocument left,PrintDocument right) {
        return Double.compare(ordervalue(left),ordervalue(right));
    }
    /**
     * Computer order
     */
    private double ordervalue(PrintDocument pd) {
        return P1*pd.getSize() + P2*pd.getTimeStamp();
    }
    
}