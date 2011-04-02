import java.util.ArrayList;
import java.util.LinkedList;

public class DisjointSet {
	private INode[] iNodes;

	public DisjointSet(ArrayList<Node> nodes) {
		iNodes = new INode[nodes.size()];
		INode newNode;
		for (Node n : nodes) {
			iNodes[n.getIndex()] = new INode(n);
		}
	}

	public Node find(Node node) {
		LinkedList<INode> backTrack = new LinkedList<INode>();
		INode current = iNodes[node.getIndex()];
		INode parent = current.getParent();
		backTrack.add(current);
		while (current != parent) {
			backTrack.add(parent);
			current = parent;
			parent = current.getParent();
		}
		for (INode iNodeInPath : backTrack) {
			iNodeInPath.setParent(parent);
		}
		return current.getNode();
	}

	public void union(Node lhs, Node rhs) {
		INode iLhs = iNodes[lhs.getIndex()];
		INode iRhs = iNodes[rhs.getIndex()];
		int iLhsSize = iLhs.getSetSize();
		int iRhsSize = iRhs.getSetSize();
		if (iLhsSize >= iRhsSize) {
			iRhs.setParent(iLhs);
			iLhs.incremetSetSize(iRhsSize);
		} else {
			iLhs.setParent(iRhs);
			iRhs.incremetSetSize(iLhsSize);
		}
	}

	private static class INode {
		private Node itsNode;
		private INode parent;
		private int setSize;

		private INode(Node node) {
			itsNode = node;
			parent = this;
			setSize = 1;
		}

		private Node getNode() {
			return itsNode;
		}

		private INode getParent() {
			return parent;
		}

		private int getSetSize() {
			return setSize;
		}
		
		private void setParent(INode newParent) {
			parent = newParent;
		}

		private void incremetSetSize(int increment) {
			setSize += increment;
		}
	}

}
