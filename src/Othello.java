/**
 * Jeux Othello
 * SAE 1.01
 * Bryan Jolle et Titouan Morineau
 */

class Othello{
	void principal(){
		int[][] tab = tabJeu(8);
		tab[0][0] = 2;
		afficheTabJeu(tab);
	}
	
	
	/**
	 * Créer un matrice en size * size pour le jeu d'Othello
	 * @param int size : taille de notre matrice 
	 * @return
	 */
	 
	int[][] tabJeu (int size){
		while (!(size >= 4 && size <= 16 && size % 2 == 0)){
			size = SimpleInput.getInt("Nombre entre 4 et 16 pair pour la taille du plateaux de jeu");
		}
		int[][] tab = new int [size][size];
		return tab;
	}
	
	/**
	 * Affiche une matrice en size * size pour le jeu d'Othello
	 */
	 
	void afficheTabJeu (int[][] tab){
		for (int i = 0; i < tab.length; i++){
			for (int y = 0; y < tab.length; y++){
				if ( tab[i][y] == 0 ){
					System.out.print("| ");
				}else if (tab[i][y] == 1){
					System.out.print("|o");
				}else if (tab[i][y] == 2){
					System.out.print("|x");
				}
				
				if ( y == tab[i].length-1 ){
					System.out.print("|\n");
				}
			}
		}
	}
}
