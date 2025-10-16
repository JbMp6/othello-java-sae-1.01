/**
 * Jeux Othello
 * SAE 1.01
 * Bryan Jolle et Titouan Morineau
 */

class Othello{
	
// Méthode Principal ------------------------------------------------------------------------------------------
	
	void principal(){
		int tailleTab = logo();
		int[][] tab = tabJeu(tailleTab);
		demarreJeu1v1(tab);
	}
	
	int logo (){
		System.out.println("");
        System.out.println("     /$$$$$$  /$$$$$$$$ /$$   /$$ /$$$$$$$$ /$$       /$$       /$$$$$$  ");
        System.out.println("    /$$__  $$|__  $$__/| $$  | $$| $$_____/| $$      | $$      /$$__  $$ ");
        System.out.println("   | $$  \\ $$   | $$   | $$  | $$| $$      | $$      | $$     | $$  \\ $$ ");
        System.out.println("   | $$  | $$   | $$   | $$$$$$$$| $$$$$   | $$      | $$     | $$  | $$ ");
        System.out.println("   | $$  | $$   | $$   | $$__  $$| $$__/   | $$      | $$     | $$  | $$ ");
        System.out.println("   | $$  | $$   | $$   | $$  | $$| $$      | $$      | $$     | $$  | $$ ");
        System.out.println("   |  $$$$$$/   | $$   | $$  | $$| $$$$$$$$| $$$$$$$$| $$$$$$$$|  $$$$$$/ ");
        System.out.println("    \\______/    |__/   |__/  |__/|________/|________|________/ \\______/  ");
        System.out.print("\n\n\n");
        int taille = SimpleInput.getInt("Sélectionner la taille de votre plateau de jeu (entre 4 et 16) : ");
        return taille;
	}
	
	/**
	 * Démarre le jeu d'Othello
	 * @param int[][] tab : taille de notre matrice 
	 */
	void demarreJeu1v1(int [][] tab){
		tab[(tab.length-1)/2][(tab.length-1)/2] = 1;
		tab[(tab.length-1)/2][(tab.length)/2] = 2;
		tab[(tab.length)/2][(tab.length-1)/2] = 2;
		tab[(tab.length)/2][(tab.length)/2] = 1;
		
		int touEnCours = 1;
		
		int[][] caseA = caseAdverse(touEnCours, tab);
		int[][] caseJouable = verifiCaseVoisin(listeCaseVoisin(caseA),tab,touEnCours);
		
		int compteur2JoueurBloque = 0;
		boolean deuxJoueurBloque = true;
		
		while(tabEstPasPlein(tab) && deuxJoueurBloque){
			
			afficheTabJeu(tab);
			
			int[] caseJoue = {0,0};
			
			if ( caseJouable.length == 0 ){
				compteur2JoueurBloque++;
				System.out.println("Aucune case jouable. Passer votre tour...");
			}else{
				caseJoue = reponsesCaseJoue(caseJouable, touEnCours);
			}
			if ( compteur2JoueurBloque == 2 ){
				deuxJoueurBloque = false;
			}
			
			
			
			placeCase( caseJoue[0], caseJoue[1], tab, touEnCours);
			retournePions(caseJoue[0], caseJoue[1], touEnCours, tab);
			
			if (touEnCours == 1){
				touEnCours = 2;
			}else{
				touEnCours = 1;
			}
			
			caseA = caseAdverse(touEnCours, tab);
			caseJouable = verifiCaseVoisin(listeCaseVoisin(caseA),tab,touEnCours);
			
			
							
			System.out.println("------------------------------------");
			
		}
		System.out.println("----------- Tab de fin -------------");
		System.out.println("------------------------------------");
		
		afficheTabJeu(tab);
	}
	
// Méthode qui gere tout ce qui verification de pions ------------------------------------------------------------------------------------------
	
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
	 * @param tabAdverse : liste des positions des pions adverses
	 * @return int[][] matrice de tous les x,y des cases voisines (peut contenir des doublons)
	 */
	int[][] listeCaseVoisin(int[][] tabAdverse) {
		
		int compt = 0;

		for (int i = 0; i < tabAdverse.length; i++) {
			int[][] rep = caseVoisin(tabAdverse[i][0], tabAdverse[i][1]);
			compt += rep.length;
		}

		int[][] listeVoisin = new int[compt][2];
		
		compt = 0;

		for (int i = 0; i < tabAdverse.length; i++) {
			int[][] rep = caseVoisin(tabAdverse[i][0], tabAdverse[i][1]);
			for (int j = 0; j < rep.length; j++) {
				listeVoisin[compt][0] = rep[j][0];
				listeVoisin[compt][1] = rep[j][1];
				compt++;
			}
		}

		return listeVoisin;
	}
	
	/**
	 * Vérifie si le joueur peut placer un pion sur la case donnée.
	 * Une case est jouable si elle encadre au moins un pion adverse
	 * entre la case testée et un autre pion du joueur dans au moins une direction.
	 */
	boolean encadrePions(int x, int y, int joueur, int[][] tab) {
		boolean estJouable = false;
		
		if (tab[x][y] == 0) { // case libre
			estJouable = verifieDirection(x, y, -1, 0, joueur, tab)  // haut
				|| verifieDirection(x, y, 1, 0, joueur, tab)   // bas
				|| verifieDirection(x, y, 0, -1, joueur, tab)  // gauche
				|| verifieDirection(x, y, 0, 1, joueur, tab)   // droite
				|| verifieDirection(x, y, -1, -1, joueur, tab) // haut-gauche
				|| verifieDirection(x, y, -1, 1, joueur, tab)  // haut-droite
				|| verifieDirection(x, y, 1, -1, joueur, tab)  // bas-gauche
				|| verifieDirection(x, y, 1, 1, joueur, tab);  // bas-droite
		}
		
		return estJouable;
	}

	/**
	 * Vérifie si, dans une direction donnée, la case encadre un ou plusieurs pions adverses.
	 * L'encadrement est valide si l'on rencontre au moins un pion adverse suivi d'un pion du joueur,
	 * sans case vide entre les deux.
	 */
	boolean verifieDirection(int x, int y, int directionX, int directionY, int joueur, int[][] tab) {
		int adverse = 0;
		if (joueur == 1) {
			adverse = 2;
		} else {
			adverse = 1;
		}
		int i = x + directionX;
		int j = y + directionY;
		boolean aVuAdverse = false;
		boolean resultat = false;
		boolean continuer = true;
		
		while (i >= 0 && i < tab.length && j >= 0 && j < tab.length && continuer) {
			if (tab[i][j] == adverse) {
				aVuAdverse = true; // on a vu un pion adverse
			} else if (tab[i][j] == joueur && aVuAdverse) {
				resultat = true; // encadrement valide
				continuer = false;
			} else {
				continuer = false; // vide ou joueur mais pas d'adversaire avant
			}
			i += directionX;
			j += directionY;
		}
		
		return resultat;
	}

	
	/**
	 * Vérifie quelles cases sont réellement jouables pour le joueur.
	 * Une case est jouable si elle est vide et encadre au moins un pion adverse.
	 * @param tabVoisin : liste des voisins de pions adverses
	 * @param tabJeu : plateau de jeu
	 * @param joueur : joueur en cours (1 ou 2)
	 * @return int[][] : matrice des cases jouables
	 */
	int[][] verifiCaseVoisin(int[][] tabVoisin, int[][] tabJeu, int joueur) {
		int compt = 0;

		// Compte combien de cases sont réellement jouables
		for (int i = 0; i < tabVoisin.length; i++) {
			int x = tabVoisin[i][0];
			int y = tabVoisin[i][1];
			if (x >= 0 && x < tabJeu.length && y >= 0 && y < tabJeu.length) {
				if (tabJeu[x][y] == 0 && encadrePions(x, y, joueur, tabJeu)) {
					compt++;
				}
			}
		}

		int[][] caseJouable = new int[compt][2];
		compt = 0;

		// Remplit le tableau avec les cases jouables
		for (int i = 0; i < tabVoisin.length; i++) {
			int x = tabVoisin[i][0];
			int y = tabVoisin[i][1];
			if (x >= 0 && x < tabJeu.length && y >= 0 && y < tabJeu.length) {
				if (tabJeu[x][y] == 0 && encadrePions(x, y, joueur, tabJeu)) {
					caseJouable[compt][0] = x;
					caseJouable[compt][1] = y;
					compt++;
				}
			}
		}

		return caseJouable;
	}

// Méthode qui gere tout ce qui est placement de la case ------------------------------------------------------------------------------------------
	
	/**
	 * Place une case sur le plateau de jeu
	 * @param int x : la ligne
	 * @return int y : la colonne
	 */
	 void placeCase(int x, int y, int[][] tab, int joueur){
		 tab[x][y] = joueur;
	 }

	
	/**
	* Montre quelles cases ont la possibilité d'être joué 
	* puis demande d'en choisir une
	* @param 
	*/
	
	int[] reponsesCaseJoue(int[][] casePossibleAJouer, int joueur) {
		String joueurTexte = "";
		
		if (joueur == 1) joueurTexte = "O";
		else joueurTexte = "X";
		
		System.out.println("Au tour des : " + joueurTexte);
		System.out.println("Voici les cases possibles à jouer (ligne, colonne) : ");
		for (int i = 0; i < casePossibleAJouer.length; i++) {
			System.out.println((casePossibleAJouer[i][0] + 1) + ", " + (casePossibleAJouer[i][1] + 1));
		}

		while (true) {
			int reponseLigne = SimpleInput.getInt("Numéro de ligne : ");
			int reponseCase = SimpleInput.getInt("Numéro de colonne : ");

			int[] reponse = {reponseLigne - 1, reponseCase - 1};

			for (int i = 0; i < casePossibleAJouer.length; i++) {
				if (casePossibleAJouer[i][0] == reponse[0] && casePossibleAJouer[i][1] == reponse[1]) {
					return reponse;
				}
			}

			System.out.println("Case invalide, veuillez en choisir une parmi la liste affichée.");
		}
	}
	
// Méthode qui gere tout ce qui est retournement de la case ------------------------------------------------------------------------------------------
	
	/**
	 * Retourne les pions adverses dans une direction donnée
	 * @param x : position ligne de la case jouée
	 * @param y : position colonne de la case jouée
	 * @param directionX : direction en x (-1, 0, ou 1)
	 * @param directionY : direction en y (-1, 0, ou 1)
	 * @param joueur : numéro du joueur (1 ou 2)
	 * @param tab : plateau de jeu
	 */
	void retourneDansDirection(int x, int y, int directionX, int directionY, int joueur, int[][] tab){
		
		int adverse = 0;
		if (joueur == 1) adverse = 2 ; 
		else adverse = 1;
		
		int i = x + directionX;
		int j = y + directionY;
		
		boolean continuer = true; 
		
		while (i >= 0 && i < tab.length && j >= 0 && j < tab.length && continuer){
			if (tab[i][j] == adverse){
				tab[i][j] = joueur;
				i += directionX;
				j += directionY;
			}else{
				continuer = false;
			}
		}
	}
	
	/**
	 * Retourne les pions adverses dans une direction donnée
	 * @param x : position ligne de la case jouée
	 * @param y : position colonne de la case jouée
	 * @param joueur : numéro du joueur (1 ou 2)
	 * @param tab : plateau de jeu
	 */
	void retournePions(int x, int y, int joueur, int[][] tab){
		if (verifieDirection(x, y, -1, 0, joueur, tab)) retourneDansDirection(x, y, -1, 0, joueur, tab);
		if (verifieDirection(x, y, 1, 0, joueur, tab)) retourneDansDirection(x, y, 1, 0, joueur, tab);
		if (verifieDirection(x, y, 0, -1, joueur, tab)) retourneDansDirection(x, y, 0, -1, joueur, tab);
		if (verifieDirection(x, y, 0, 1, joueur, tab)) retourneDansDirection(x, y, 0, 1, joueur, tab);
		if (verifieDirection(x, y, -1, -1, joueur, tab)) retourneDansDirection(x, y, -1, -1, joueur, tab);
		if (verifieDirection(x, y, -1, 1, joueur, tab)) retourneDansDirection(x, y, -1, 1, joueur, tab);
		if (verifieDirection(x, y, 1, -1, joueur, tab)) retourneDansDirection(x, y, 1, -1, joueur, tab);
		if (verifieDirection(x, y, 1, 1, joueur, tab)) retourneDansDirection(x, y, 1, 1, joueur, tab);
		
	}
	
// Méthode qui gère les conditions d'arrêt du jeu -----------------------------------------------------------------------------------------------
	
	boolean tabEstPasPlein(int[][] tab){
		boolean estPlein = true;
		for ( int i = 0; i < tab.length; i++){
			for ( int y = 0; y < tab[i].length; y++){
				if ( tab[i][y] == 0 ) {
					estPlein = false;
				}
			}
		}
		return !estPlein;
	}
	
	
// Méthode qui gère affichage et créations du plateaux ------------------------------------------------------------------------------------------
	
	/**
	 * Créer un matrice en size * size pour le jeu d'Othello
	 * @param int size : taille de notre matrice 
	 * @return tab : matrice en size*size
	 */
	int[][] tabJeu(int size){
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

				if ( y == tab[i].length-1 ){
					System.out.print("|\n");
				}
				
			}
		}
		System.out.print("\n");
	}

}
