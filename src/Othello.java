/**
 * Jeux Othello
 * SAE 1.01
 * Bryan Jolle et Titouan Morineau
 */

class Othello{
	void principal(){
		int[][] tab = tabJeu(8);
		demarreJeu(tab);
	}
	
	/**
	 * Démarre le jeu d'Othello
	 * @param int[][] tab : taille de notre matrice 
	 */
	void demarreJeu (int [][] tab){
		tab[(tab.length-1)/2][(tab.length-1)/2] = 1;
		tab[(tab.length-1)/2][(tab.length)/2] = 2;
		tab[(tab.length)/2][(tab.length-1)/2] = 2;
		tab[(tab.length)/2][(tab.length)/2] = 1;
		
		afficheTabJeu(tab);
	}
	
	
	/**
	 * Créer un matrice en size * size pour le jeu d'Othello
	 * @param int size : taille de notre matrice 
	 * @return tab : matrice en size*size
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
	 * @param int[][] tab : la matrice a afficher
	 */
	void afficheTabJeu (int[][] tab){
		
		System.out.print("\n");
		System.out.print("\t   ");
		
		for (int i = 0; i < tab.length; i++){
			if ( i < 9){
				System.out.print("  " + (i+1) );
			}else{
				System.out.print(" " + (i+1) );
			}
		}
		
		System.out.print("\n");
		
		for (int i = 0; i < tab.length; i++){
			if ( i < 9){
				System.out.print("\t" + (i+1) +"  ");
			}else{
				System.out.print("\t" + (i+1) +" ");
			}
			
			for (int y = 0; y < tab.length; y++){
				if ( tab[i][y] == 0 ){
					System.out.print("|  ");
					
				}else if (tab[i][y] == 1){
					System.out.print("|o ");
					
				}else if (tab[i][y] == 2){
					System.out.print("|x ");
					
			}
				
				
				
				if ( y == tab[y].length-1 ){
					System.out.print("|\n");
				}
				
			}
		}
	}
}
