package GAME; 
public class SolitaireToCanvas extends Solitaire{
	public int getCard(int stack, int index){
		System.out.println(index/100);
		switch(stack){
			case 1 : return (stock.isEmpty() ? 10000 : 155);
			case 2 : return (talon.iTableau(talon.getSize()-1-index) != null ? talon.iTableau(talon.getSize()-1-index).getImageNum() : 10000);
			case 3 : return (foundations[index/100].iTableau(foundations[index/100].getSize()-1-(index%100)) != null ? foundations[index/100].iTableau(index%100).getImageNum() : 10000);
			case 4 : return (tableau[index/100].iTableau(tableau[index/100].getSize()-1-(index%100)) != null ? tableau[index/100].iTableau(index%100).getImageNum() : 10000);
		}
		return 10000;
	}
} 