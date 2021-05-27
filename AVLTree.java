public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotation<E> {
    private boolean increase;
    private boolean decrease;


    private static class AVLNode<E extends Comparable<E>> extends Node<E> {
        public static final int LEFT_HEAVY=-1;
        public static final int BALANCED=0;
        public static final int RIGHT_HEAVY=1;
        private int balance;

        public AVLNode(E item) {
            super(item);
            balance=BALANCED;
        }

        @Override
        public String toString() {
            return balance + ": " + super.toString();
        }
    }
    @Override
    public boolean add(E item) {
        increase=false;
        root=add( (AVLNode < E > ) root, item);
        return addReturn;
    }
    private AVLNode<E> add(AVLNode<E> localRoot,E item) {
        //root is null
        if(localRoot==null) {
            addReturn=true;
            increase=true;
            return new AVLNode<>(item);
        }
        int result=item.compareTo(localRoot.data);
        //item is at the local root
        if(result==0){
            increase=false;
            addReturn=false;
            return localRoot;
        }
        //item<data
        else if(result<0) {
            localRoot.left=add((AVLNode<E>)localRoot.left, item);
            if(increase) {
                decrementBalance(localRoot);
                if(localRoot.balance<AVLNode.LEFT_HEAVY) {
                    increase=false;
                    return rebalanceLeft(localRoot);
                }
            }
            return localRoot;
        }
        else {
            //item>data
            localRoot.right=add((AVLNode<E>) localRoot.right,item);
            if(increase) {
                incrementBalance(localRoot);
                if(localRoot.balance>AVLNode.RIGHT_HEAVY) {
                    return rebalanceRight(localRoot);
                }
                else {
                    return localRoot;
                }
    
            }
            else {
                return localRoot;
            }
        }
    }
    @Override
    public E delete(E item) {
        decrease=false;
        root=delete((AVLNode<E>)root,item);
        return deleteReturn;
    }
    private AVLNode<E> delete(AVLNode<E> localRoot,E item) {
        if(localRoot==null) {
            deleteReturn=null;
            return localRoot;
        }
        int result=item.compareTo(localRoot.data);
        if(result==0) {
            //item is in the tree , remove it
            deleteReturn=localRoot.data;
            return findReplacementNode(localRoot);
        }
        else if(result<0) {
            //item<localroot.data
            localRoot.left=delete((AVLNode<E>)localRoot.left,item);
            if(decrease) {
                incrementBalance(localRoot);
                if(localRoot.balance>AVLNode.RIGHT_HEAVY) {
                    return rebalanceRightL(localRoot);
                }
                else {
                    return localRoot;
                }
            }
            else {
                return localRoot;
            }
        }
        else {
            //item>localroot.data
            localRoot.right=delete((AVLNode<E>)localRoot.right, item);
            if(decrease) {
                decrementBalance(localRoot);
                if(localRoot.balance<AVLNode.LEFT_HEAVY) {
                    return rebalanceLeftR(localRoot);
                }
                else {
                    return localRoot;
                }
            }
            else {
                return localRoot;
            }
        }
    }
    private AVLNode < E > rebalanceLeft(AVLNode < E > localRoot) {
        // Obtain reference to left child.
        AVLNode < E > leftChild = (AVLNode < E > ) localRoot.left;
        // See whether left-right heavy.
        if (leftChild.balance > AVLNode.BALANCED) {
          // Obtain reference to left-right child.
          AVLNode < E > leftRightChild = (AVLNode < E > ) leftChild.right;
          /** Adjust the balances to be their new values after
              the rotations are performed.
           */
          if (leftRightChild.balance < AVLNode.BALANCED) {
            leftChild.balance = AVLNode.BALANCED;
            leftRightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.RIGHT_HEAVY;
          }
          else {
            leftChild.balance = AVLNode.LEFT_HEAVY;
            leftRightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
          }
          // Perform left rotation.
          localRoot.left = rotateLeft(leftChild);
        }
        else { //Left-Left case
          /** In this case the leftChild (the new root)
              and the root (new right child) will both be balanced
              after the rotation.
           */
          leftChild.balance = AVLNode.BALANCED;
          localRoot.balance = AVLNode.BALANCED;
        }
        // Now rotate the local root right.
        return (AVLNode < E > ) rotateRight(localRoot);
    }

    private AVLNode < E > rebalanceRight(AVLNode < E > localRoot) {
        // Obtain reference to right child
        AVLNode < E > rightChild = (AVLNode < E > ) localRoot.right;
        // See if right-left heavy
        if (rightChild.balance < AVLNode.BALANCED) {
            // Obtain reference to right-left child
            AVLNode < E > rightLeftChild = (AVLNode < E > ) rightChild.left;
            /* Adjust the balances to be their new values after
                the rotates are performed.
            */
            if (rightLeftChild.balance > AVLNode.BALANCED) {
            rightChild.balance = AVLNode.BALANCED;
            rightLeftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.LEFT_HEAVY;
            }
            else if (rightLeftChild.balance < AVLNode.BALANCED) {
            rightChild.balance = AVLNode.RIGHT_HEAVY;
            rightLeftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
            }
            else {
            rightChild.balance = AVLNode.BALANCED;
            rightLeftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
            }
            /** After the rotates the overall height will be
                 reduced thus increase is now false, but
                decrease is now true.
            */
            increase = false;
            decrease = true;
            // Perform double rotation
            localRoot.right = rotateRight(rightChild);
            return (AVLNode < E > ) rotateLeft(localRoot);
        }
        else {
            /* In this case both the rightChild (the new root)
                and the root (new left child) will both be balanced
                after the rotate. Also the overall height will be
                reduced, thus increase will be fasle, but decrease
                will be true.
            */
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
            increase = false;
            decrease = true;
            // Now rotate the
            return (AVLNode < E > ) rotateLeft(localRoot);
        }
    }
    private AVLNode<E> findReplacementNode(AVLNode<E> node) {
        if(node.left==null) {
            decrease=true;
            return (AVLNode<E>)node.right;
        }
        else if(node.right==null) {
            decrease=true;
            return (AVLNode<E>)node.left;
        }
        else {
            if(node.left.right==null) {
                node.data=node.left.data;
                node.left=node.left.left;
                incrementBalance(node);
                return node;
            }
            else {
                node.data=findLargestChild((AVLNode<E>)node.left);
                if(((AVLNode<E>)node.left).balance<AVLNode.LEFT_HEAVY) {
                    node.left=rebalanceLeft((AVLNode<E>)node.left);
                }
                if(decrease) {
                    incrementBalance(node);
                }
                return node;
            }
        }
    }
    private E findLargestChild(AVLNode<E> parent) {
        if(parent.right.right==null) {
            E returnValue=parent.right.data;
            parent.right=parent.right.left;
            decrementBalance(parent);
            return returnValue;
        }
        else {
            E returnValue=findLargestChild((AVLNode<E>)parent.right);
            if(((AVLNode<E>)parent.right).balance<AVLNode.LEFT_HEAVY) {
                parent.right=rebalanceLeft((AVLNode<E>)parent.right);
            }
            if(decrease) {
                decrementBalance(parent);
            }
            return returnValue;
        }
    }
    private void decrementBalance(AVLNode < E > node) {
        // Decrement the balance.
        node.balance--;
        if (node.balance == AVLNode.BALANCED) {
          /** If now balanced, overall height has not increased. */
          increase = false;
        }
    }
    private AVLNode < E > rebalanceLeftR(AVLNode < E > localRoot) {
        // Obtain reference to left child
        AVLNode < E > leftChild = (AVLNode < E > ) localRoot.left;
        // See if left-right heavy
        if (leftChild.balance > AVLNode.BALANCED) {
          // Obtain reference to left-right child
          AVLNode < E > leftRightChild = (AVLNode < E > ) leftChild.right;
          /* Adjust the balances to be their new values after
             the rotates are performed.
           */
          if (leftRightChild.balance < AVLNode.BALANCED) {
            leftChild.balance = AVLNode.LEFT_HEAVY;
            leftRightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
          }
          else if (leftRightChild.balance > AVLNode.BALANCED) {
            leftChild.balance = AVLNode.BALANCED;
            leftRightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.RIGHT_HEAVY;
          }
          else {
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
          }
          /** After the rotates the overall height will be
              reduced thus increase is now false, but
              decrease is now true.
           */
          increase = false;
          decrease = true;
          // Perform double rotation
          localRoot.left = rotateLeft(leftChild);
          return (AVLNode < E > ) rotateRight(localRoot);
        }
        if (leftChild.balance < AVLNode.BALANCED) {
          /* In this case both the leftChild (the new root)
             and the root (new right child) will both be balanced
             after the rotate. Also the overall height will be
             reduced, thus increase will be fasle, but decrease
             will be true.
           */
          leftChild.balance = AVLNode.BALANCED;
          localRoot.balance = AVLNode.BALANCED;
          increase = false;
          decrease = true;
        }
        else {
          /* After the rotate the leftChild (new root) will
             be right heavy, and the local root (new right child)
             will be left heavy. The overall height of the tree
             will not change, thus increase and decrease remain
             unchanged.
           */
          leftChild.balance = AVLNode.RIGHT_HEAVY;
          localRoot.balance = AVLNode.LEFT_HEAVY;
        }
        // Now rotate the
        return (AVLNode < E > ) rotateRight(localRoot);
      }
      private AVLNode < E > rebalanceRightL(AVLNode < E > localRoot) {
        // Obtain reference to right child
        AVLNode < E > rightChild = (AVLNode < E > ) localRoot.right;
        // See if right-left heavy
        if (rightChild.balance < AVLNode.BALANCED) {
          // Obtain reference to right-left child
          AVLNode < E > rightLeftChild = (AVLNode < E > ) rightChild.left;
          /* Adjust the balances to be their new values after
             the rotates are performed.
           */
          if (rightLeftChild.balance > AVLNode.BALANCED) {
            rightChild.balance = AVLNode.RIGHT_HEAVY;
            rightLeftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
          }
          else if (rightLeftChild.balance < AVLNode.BALANCED) {
            rightChild.balance = AVLNode.BALANCED;
            rightLeftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.LEFT_HEAVY;
          }
          else {
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
          }
          /** After the rotates the overall height will be
              reduced thus increase is now false, but
              decrease is now true.
           */
          increase = false;
          decrease = true;
          // Perform double rotation
          localRoot.right = rotateRight(rightChild);
          return (AVLNode < E > ) rotateLeft(localRoot);
        }
        if (rightChild.balance > AVLNode.BALANCED) {
          /* In this case both the rightChild (the new root)
             and the root (new left child) will both be balanced
             after the rotate. Also the overall height will be
             reduced, thus increase will be fasle, but decrease
             will be true.
           */
          rightChild.balance = AVLNode.BALANCED;
          localRoot.balance = AVLNode.BALANCED;
          increase = false;
          decrease = true;
        }
        else {
          /* After the rotate the rightChild (new root) will
             be left heavy, and the local root (new left child)
             will be right heavy. The overall height of the tree
             will not change, thus increase and decrease remain
             unchanged.
           */
          rightChild.balance = AVLNode.LEFT_HEAVY;
          localRoot.balance = AVLNode.RIGHT_HEAVY;
        }
        // Now rotate the
        return (AVLNode < E > ) rotateLeft(localRoot);
      }
      private void incrementBalance(AVLNode < E > node) {
        node.balance++;
        if (node.balance > AVLNode.BALANCED) {
          /* if now right heavy, the overall height has increased, but
             it has not decreased */
          increase = true;
          decrease = false;
        }
        else {
          /* if now balanced, the overall height has not increased, but
             it has decreased. */
          increase = false;
          decrease = true;
        }
      }
}