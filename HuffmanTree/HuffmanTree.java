import java.util.*;
import java.io.*;

public class HuffmanTree implements Serializable{
    protected BinaryTree<HuffData> huffTree;

    //Builds the Huffman tree using the given alphabet and weights.
    public void buildTree(HuffData[] symbols) {
        Queue<BinaryTree<HuffData>> theQueue=new PriorityQueue<BinaryTree<HuffData>>(symbols.length,new CompareHuffmannTrees());
        for(HuffData nextSymbol:symbols) {
            BinaryTree<HuffData> aBinaryTree=new BinaryTree<HuffData>(nextSymbol,null,null);
            theQueue.offer(aBinaryTree);
        }
        while (theQueue.size() > 1) {
            BinaryTree < HuffData > left = theQueue.poll();
            BinaryTree < HuffData > right = theQueue.poll();
            double wl = left.getData().weight;
            double wr = right.getData().weight;
            HuffData sum = new HuffData(wl + wr, null);
            BinaryTree < HuffData > newTree =
                new BinaryTree < HuffData > (sum, left, right);
            theQueue.offer(newTree);
          }
      
          // The queue should now contain only one item.
          huffTree = theQueue.poll();
    }
    //Decodes a message using the generated Huffman tree
    public String decode(String codedMessage) {
        StringBuilder result = new StringBuilder();
        BinaryTree < HuffData > currentTree = huffTree;
        for (int i = 0; i < codedMessage.length(); i++) {
          if (codedMessage.charAt(i) == '1') {
            currentTree = currentTree.getRightSubtree();
          }
          else {
            currentTree = currentTree.getLeftSubtree();
          }
          if (currentTree.isLeaf()) {
            HuffData theData = currentTree.getData();
            result.append(theData.symbol);
            currentTree = huffTree;
          }
        }
        return result.toString();
    }
    //Outputs the resulting code
    private void printCode(PrintStream out,String code,BinaryTree<HuffData> tree) {
        HuffData theData = tree.getData();
        if (theData.symbol != null) {
          if (theData.symbol.equals(" ")) {
            out.println("space: " + code);
          }
          else {
            out.println(theData.symbol + ": " + code);
          }
        }
        else {
          printCode(out, code + "0", tree.getLeftSubtree());
          printCode(out, code + "1", tree.getRightSubtree());
        }
    }
    public void printCode(PrintStream out) {
        printCode(out,"",huffTree);
    }
    public static class HuffData implements Serializable {
        private double weight;
        private Character symbol;
        
        public HuffData(double weight,Character symbol) {
            this.weight=weight;
            this.symbol=symbol;
        }
        public Character getSymbol() {return symbol;}
    }
    
    private static class CompareHuffmannTrees implements Comparator<BinaryTree<HuffData>> {
        public int compare(BinaryTree<HuffData> treeLeft,BinaryTree<HuffData> treeRight) {
            double wleft=treeLeft.getData().weight;
            double wRight=treeRight.getData().weight;
            return Double.compare(wleft,wRight);
        }
    }
}