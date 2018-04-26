import java.util.*;
import java.lang.Math.*;
public class CA{
	public static void main(String[] args){
		Random r = new Random();
		Scanner s = new Scanner(System.in);
		System.out.print("Enter number of cells: ");
		int number_of_cells = s.nextInt();
		int count = 0;
		double p_t = 0;
		int[] ca = new int[number_of_cells];
		for(int i = 0;i < number_of_cells;i++){
			ca[i] = 0;
		}
		System.out.println("CA setup complete!");
		System.out.print("Enter which cells are initially tumor cells [0, "+ (number_of_cells - 1) +"] (comma separated): ");
		s.nextLine();
		String input_string = s.nextLine();
		String[] inputs = input_string.split(",");
		System.out.print("Time t at : ");
		String input_time = s.nextLine();

		for(int i = 0;i < inputs.length;i++){
			ca[Integer.parseInt(inputs[i])] = 1;
		}
		int time = Integer.parseInt(input_time);
		System.out.print("A = ");
		input_string = s.nextLine();
		double A = Double.parseDouble(input_string);
		System.out.print("alpha = ");
		input_string = s.nextLine();
		double alpha = Double.parseDouble(input_string);
		System.out.print("radius = ");
		input_string = s.nextLine();
		double radius = Double.parseDouble(input_string);
		double cellVolume = Math.pow(radius,3);
		System.out.println(time);	
		for(int j = 1; j <= time; j++){
			int[] ca_next = new int[number_of_cells];
			for(int i = 0;i < number_of_cells;i++){
				ca_next[i] = ca[i];
			}
			System.out.println();
			for(int i = 0;i < number_of_cells;i++){
				if(ca[i] == 1){
					ca_next[i] = 1;
				}
				else if(ca[Math.floorMod(i + 1,number_of_cells)] == ca[Math.floorMod(i-1, number_of_cells)]){
					ca_next[i] = ca[Math.floorMod(i + 1,number_of_cells)];
				}
				else{
					double w_ca_t = A*(cellVolume)*(Math.exp((A/alpha)*(1-Math.exp(-alpha*j)))*Math.exp((-alpha*j)));
					p_t = w_ca_t / (cellVolume*2);
					while (p_t >= 1.0){
						ca_next[i] = 1;
						ca[i] = 1;
						p_t = p_t - 1.0;
						if( i == number_of_cells-1 || i == 0){
							break;
						}
						else{
							if(ca[i+1] == 1){
								i = i-1;
							}
							else if (ca[i-1] == 1){
								i = i+1;
							}
						}
					}
					if(r.nextDouble()<p_t){
						ca_next[i] = 1;
					}
				}
			}
			ca = ca_next;
			ca_next = null;
		}

		for(int i = 0 ; i < number_of_cells ; i++){
			if(ca[i] == 1){
				count = count + 1;
			}
		}
		System.out.println();
		System.out.println("Cellular Automata result : " + cellVolume*count);
		System.out.println("Gompertz Equation result : " + cellVolume*Math.exp((A/alpha)*(1-Math.exp(-alpha*time))));
	}
}