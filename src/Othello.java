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
		
		int touEnCours = 1;
		
		for (int i = 0; i < 9; i++){
			System.out.println("\t------------------------------------");
			afficheTabJeu(tab);
			
			int[][] caseA = caseAdverse(touEnCours, tab);
			
			if (touEnCours == 1){
				touEnCours = 2;
			}else{
				touEnCours = 1;
			}
		}
	}
	
	/**
	 * Case possible a jouer pour un tour pour x ou o
	 * @param tour : tour du joueur
	 * @param tab : la table de jeu
	 * @return int[][] matrice des x,y de pions adverse
	 */
	int[][] caseAdverse(int tour, int[][] tab) {
		
		int compt = 0;
		int adverse;
		
		if ( tour == 1 ){
			adverse = 2;
		}else{
			adverse = 1;
		}
		
		for ( int i = 0; i < tab.length; i++){
			for ( int y = 0; y < tab[i].length; y++){
				if ( tab[i][y] == adverse ){
					compt++;
				}
			}
		}
		
		int[][] tabAdverse = new int[compt][2];
		
		compt = 0;
		
		for ( int i = 0; i < tab.length; i++){
			for ( int y = 0; y < tab[i].length; y++){
				if ( tab[i][y] == adverse ){
					tabAdverse[compt] = new int[]{i,y};
					compt++;
				}
			}
		}
		return tabAdverse;
	}
	
	/**
	 * Cherche voisins d'une case x, y
	 * @param x ligne de la case
	 * @param y position de la case dans la ligne
	 * @return int[][] matrice des x,y des cases voisines
	 */
	int[][] caseVoisin(int x, int y) {
		int[][] tabVoisin = new int[8][2];
		
		tabVoisin[0] = new int[]{x-1, y};
		tabVoisin[1] = new int[]{x-1, y+1};
		tabVoisin[2] = new int[]{x, y+1};
		tabVoisin[3] = new int[]{x+1, y+1};
		tabVoisin[4] = new int[]{x+1, y};
		tabVoisin[5] = new int[]{x+1, y-1};
		tabVoisin[6] = new int[]{x, y-1};
		tabVoisin[7] = new int[]{x-1, y-1};
		
		return tabVoisin;
	}
	
	/**
	 * Liste les voisins de plusieurs cases
	 * @param tabAdverse : liste des position des pions adverse
	 * @return int[][] matrice de tout les x,y des cases voisines
	 */
	int[][] listeCaseVoisin(int[][] tabAdverse) {
		
		int compt = 0;
		
		for ( int i = 0; i < tabAdverse.length; i++){
			 
			 int[][] rep = caseVoisin(tabAdverse[i][0], tabAdverse[i][1]);
			 
			 compt = compt + rep.length;
		}
		
		int[][] listeVoisin = new int[compt][2];
		//arrete ici
		
		return listeVoisin;
	}
	
	/**
	 * Verifi si les voisin d'une case sont des case jouable
	 * @param tabVoisin : liste des voisin d'une case
	 * @param tabJeu : table de jeu
	 * @return int[][] matrice des x,y des cases jouable
	 */
	int[][] verifiCaseVoisin(int[][] tabVoisin, int[][] tabJeu) {
		
		int compt = 0;
		
		for ( int i = 0; i < tabVoisin.length; i++){
			if ( tabJeu[tabVoisin[i][0]][tabVoisin[i][1]] == 0 ){
				compt++;
			}
		}
		
		int[][] caseJouable = new int[compt][2];
		
		compt = 0;
		
		for ( int i = 0; i < tabVoisin.length; i++){
			if ( tabJeu[tabVoisin[i][0]][tabVoisin[i][1]] == 0 ){
				caseJouable[compt] = new int[]{tabVoisin[i][0],tabVoisin[i][1]};
				compt++;
			}
		}
				
		return caseJouable;
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
	
	/**
	* Montre quelles cases ont la possibilité d'être joué 
	* puis demande d'en choisir une
	* @param 
	*/
	
	int[] reponsesCaseJoue (int[][] casePossibleAJouer){
		System.out.println("Voici les cases possibles à jouer : ");
		for (int i=0; i<casePossibleAJouer.length; i++) {
			System.out.println(casePossibleAJouer[i][0]+", "+casePossibleAJouer[i][1]);
		}
		
		while(true) {
			int reponseLigne = SimpleInput.getInt("La ligne que vous voulez joué : ");
			int reponseCase = SimpleInput.getInt("La case que vous voulez joué : ");
			
			int[] reponse = {reponseLigne, reponseCase};
			
			for (int i=0; i<casePossibleAJouer.length; i++) {
				if ((casePossibleAJouer[i][0] == reponse[0]) && (casePossibleAJouer[i][1] == reponse[1])) {
					
					return reponse;
				}
			}
		}
	}
}
