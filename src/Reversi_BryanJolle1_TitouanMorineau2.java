/**
 * Jeux Othello
 * SAE 1.01
 * Bryan Jolle et Titouan Morineau
 */

class Reversi_BryanJolle1_TitouanMorineau2{
	
// Methode Principal ------------------------------------------------------------------------------------------
	
	/**
	 * Démarre tout le programme
	 */
	void principal(){
		// Récupère les paramètres de jeu (taille, mode, joueur)
		int[] info = initialisation();
		// Crée le plateau de jeu vide
		int[][] tab = tabJeu(info[0]);
		// Lance le mode de jeu sélectionné
		if (info[1] == 1){
			demarreJeu1v1(tab);
		}else if (info[1] == 2){
			demarreJeuRandomBot(tab,info[2]);
		}else if (info[1] == 3){
			demarreJeuRandomBotPlus(tab,info[2]);
		}
	}
	
	/**
	* Initialise le jeu, affiche le menu et recupere les choix du joueur
	* @param int[] info : tableau contenant la taille plateau, le mode de jeu et le pion du/des joueur(s)
	* @return int[] info (la taille plateau, le mode de jeu et le pion du/des joueur(s))
	*/
	int[] initialisation (){
		// Exécute les tests unitaires au démarrage
		testCaseVoisin();
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
        // Stocke les 3 paramètres : taille plateau, mode de jeu, choix du pion
        int[] info = new int[3];
        info[0] = SimpleInput.getInt("Selectionner la taille de votre plateau de jeu (entre 4 et 16) : ");
        System.out.println("Veuillez selectionner votre mode de jeu :\n1. 1V1 (Tour par tour sur le clavier)\n2. RandomBot\n3. RandomBot+ (Prend le coup qui rapport le plus de point)");
        info[1] = SimpleInput.getInt("");
        // Demande le choix du pion uniquement pour les modes contre bot
        if (info[1] > 1){
			System.out.println("Veuillez selectionner votre pion ( Les O commence ) :\n1. O\n2. X");
			info[2] = SimpleInput.getInt("");
			System.out.println("\n\n\n------------------------------------");
		}
        return info;
	}
	
	/**
	 * Demarre le jeu d'Othello 1V1
	 * @param int[][] tab : taille de notre matrice (plateau de jeu)
	 */
	void demarreJeu1v1(int [][] tab){
		// Place les 4 pions initiaux au centre du plateau
		tab[(tab.length-1)/2][(tab.length-1)/2] = 1;
		tab[(tab.length-1)/2][(tab.length)/2] = 2;
		tab[(tab.length)/2][(tab.length-1)/2] = 2;
		tab[(tab.length)/2][(tab.length)/2] = 1;
		
		// Le joueur O (1) commence toujours
		int touEnCours = 1;
		
		// Calcule les cases jouables pour le premier tour
		int[][] caseA = caseAdverse(touEnCours, tab);
		int[][] caseJouable = verifiCaseVoisin(listeCaseVoisin(caseA),tab,touEnCours);
		
		// Détecte si les deux joueurs sont bloqués consécutivement
		int compteur2JoueurBloque = 0;
		boolean deuxJoueurBloque = true;
		
		while(tabEstPasPlein(tab) && deuxJoueurBloque){
			
			afficheTabJeu(tab);
			
			int[] caseJoue = {0,0};
			
			// Passe le tour si aucune case n'est jouable
			if ( caseJouable.length == 0 ){
				compteur2JoueurBloque++;
				System.out.println("Aucune case jouable. Passer votre tour...");
			}else{
				compteur2JoueurBloque = 0;
				caseJoue = reponsesCaseJoue(caseJouable, touEnCours);
				placeCase( caseJoue[0], caseJoue[1], tab, touEnCours);
				retournePions(caseJoue[0], caseJoue[1], touEnCours, tab);
			}
			// Arrête la partie si les deux joueurs passent leur tour
			if ( compteur2JoueurBloque == 2 ){
				deuxJoueurBloque = false;
			}
			
			
			touEnCours = adversere(touEnCours);
			
			// Recalcule les cases jouables pour le prochain tour
			caseA = caseAdverse(touEnCours, tab);
			caseJouable = verifiCaseVoisin(listeCaseVoisin(caseA),tab,touEnCours);
			
			
							
			System.out.println("\n\n\n------------------------------------");
			
			int[] points = pointsJoueurs(tab);
			
			System.out.println("Joueur X : " + points[0] + "		Joueur O : " + points[1]);
			
			
		}
		
		System.out.println("----------- Tab de fin -------------");
		System.out.println();
		
		afficheTabJeu(tab);
		
		if ( SimpleInput.getInt("Voulez-vous rejouez ? Si oui, appuyez sur 1") == 1) {principal();}
	}
	
	/**
	 * Demarre le jeu d'Othello RamdomBot
	 * @param int[][] tab : taille de notre matrice (plateau de jeu)
	 * @param int joueurDebut : joueur qui commence
	 */
	void demarreJeuRandomBot(int [][] tab, int joueur){
		// Place les 4 pions initiaux au centre du plateau
		tab[(tab.length-1)/2][(tab.length-1)/2] = 1;
		tab[(tab.length-1)/2][(tab.length)/2] = 2;
		tab[(tab.length)/2][(tab.length-1)/2] = 2;
		tab[(tab.length)/2][(tab.length)/2] = 1;
		
		// Le joueur O (1) commence toujours
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
				compteur2JoueurBloque = 0;
				// Le joueur humain choisit, le bot joue aléatoirement
				if ( touEnCours == joueur ){
					caseJoue = reponsesCaseJoue(caseJouable, touEnCours);
				}else{
					caseJoue = caseJouable[(int)(Math.random() * (caseJouable.length-1))];
					SimpleInput.getChar("Le bot va joue. Appuyer sur une lettre pour continuer ...");
				}
				placeCase( caseJoue[0], caseJoue[1], tab, touEnCours);
				retournePions(caseJoue[0], caseJoue[1], touEnCours, tab);
			}
			if ( compteur2JoueurBloque == 2 ){
				deuxJoueurBloque = false;
			}
			
			
			touEnCours = adversere(touEnCours);
			
			caseA = caseAdverse(touEnCours, tab);
			caseJouable = verifiCaseVoisin(listeCaseVoisin(caseA),tab,touEnCours);
			
			
							
			System.out.println("\n\n\n------------------------------------");
			
			int[] points = pointsJoueurs(tab);
			
			System.out.println("Joueur X : " + points[0] + "		Joueur O : " + points[1]);
			
			
		}
		
		System.out.println("----------- Tab de fin -------------");
		System.out.println();
		
		afficheTabJeu(tab);
		
		if ( SimpleInput.getInt("Voulez-vous rejouez ? Si oui, appuyez sur 1") == 1) {principal();}
	}
	
	/**
	 * Demarre le jeu d'Othello RamdomBot+
	 * @param int[][] tab : taille de notre matrice (plateau de jeu)
	 * @param int joueurDebut : joueur qui commence
	 */
	void demarreJeuRandomBotPlus(int [][] tab, int joueur){
		// Place les 4 pions initiaux au centre du plateau
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
				compteur2JoueurBloque = 0;
				if ( touEnCours == joueur ){
					caseJoue = reponsesCaseJoue(caseJouable, touEnCours);
				}else{
					// Le bot+ filtre d'abord les meilleures cases avant de choisir
					caseJouable = meilleuresCases(caseJouable, tab, touEnCours);
					caseJoue = caseJouable[(int)(Math.random() * (caseJouable.length-1))];
					SimpleInput.getChar("Le bot va joue. Appuyer sur une lettre pour continuer ...");
				}
				placeCase( caseJoue[0], caseJoue[1], tab, touEnCours);
				retournePions(caseJoue[0], caseJoue[1], touEnCours, tab);
			}
			if ( compteur2JoueurBloque == 2 ){
				deuxJoueurBloque = false;
			}
			
			
			touEnCours = adversere(touEnCours);
			
			caseA = caseAdverse(touEnCours, tab);
			caseJouable = verifiCaseVoisin(listeCaseVoisin(caseA),tab,touEnCours);
			
			
							
			System.out.println("\n\n\n------------------------------------");
			
			int[] points = pointsJoueurs(tab);
			
			System.out.println("Joueur X : " + points[0] + "		Joueur O : " + points[1]);
			
			
		}
		
		System.out.println("----------- Tab de fin -------------");
		System.out.println();
		
		afficheTabJeu(tab);
		
		if ( SimpleInput.getInt("Voulez-vous rejouez ? Si oui, appuyez sur 1") == 1) {principal();}
	}
	
	/**
	* Teste la fonction caseVoisin() avec des exemples de cases
	*/		
	void testCaseVoisin(){
		System.out.println ();
		System.out.println ("*** testCaseVoisin()");
		
		// Test avec une case centrale (2,2)
		int[][] voisins_2_2 = {{1,2}, {1,3}, {2,3}, {3,3}, {3,2}, {3,1}, {2,1}, {1,1}};
		testCasCaseVoisin(2, 2, voisins_2_2);
		
		// Test avec une autre case centrale (4,4)
		int[][] voisins_4_4 = {{3,4}, {3,5}, {4,5}, {5,5}, {5,4}, {5,3}, {4,3}, {3,3}};
		testCasCaseVoisin(4, 4, voisins_4_4);
		
		// Test avec un coin (0,0) - vérifie les coordonnées négatives
		int[][] voisins_0_0 = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};
		testCasCaseVoisin(0, 0, voisins_0_0);
	}
	
	/**
	* Teste si les voisins d'une case sont corrects
	* @param int x : ligne de la case
	* @param int y : colonne de la case
	* @param int[][] caseVoisinsAttendu : matrice des positions attendues
	*/
	void testCasCaseVoisin(int x, int y, int[][] caseVoisinsAttendu) {
		System.out.print("CaseVoisin (" + x + ", " + y + ")\t= ");
		
		// Exécute la fonction à tester
		int[][] resExec = caseVoisin(x, y);
		
		// Affiche le résultat obtenu
		System.out.print("[");
		for (int i = 0; i < resExec.length; i++) {
			System.out.print("[");
			for (int j = 0; j < resExec[i].length; j++) {
				System.out.print(resExec[i][j]);
				if (j < resExec[i].length - 1) System.out.print(", ");
			}
			System.out.print("]");
			if (i < resExec.length - 1) System.out.print(", ");
		}
		System.out.print("]\t : ");
		
		// Compare élément par élément avec le résultat attendu
		boolean estEgal = (resExec.length == caseVoisinsAttendu.length);
		
		int i = 0;
		while (i < resExec.length && estEgal) {
			estEgal = (resExec[i].length == caseVoisinsAttendu[i].length);
			
			int j = 0;
			while (j < resExec[i].length && estEgal) {
				estEgal = (resExec[i][j] == caseVoisinsAttendu[i][j]);
				j++;
			}
			i++;
		}
		
		if (estEgal) {
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
// Methode qui gere tout ce qui est verification des pions ------------------------------------------------------------------------------------------
	
	/**
	 * Compte le nombre de points de chaque joueur en fonction de leur
	 * nombre de pions sur le plateau
	 * @param int[][] tab : plateau de jeu
	 * @return int[] points : tableau contenant [points X, points O]
	 */
	int[] pointsJoueurs(int[][] tab) {
		int pointX = 0;
		int pointO = 0;
		// Parcourt tout le plateau pour compter les pions
		for ( int i = 0; i < tab.length; i++){
			for ( int y = 0; y < tab[i].length; y++){
				// Compte les pions X (joueur 2)
				if ( tab[i][y] == 2 ) {
					pointX++;
				// Compte les pions O (joueur 1)
				} else if (tab[i][y] == 1) {
					pointO++;
				}
			}
		}
		int[] points = {pointX, pointO};
		return points;
	}
	
	/**
	 * Case possible a jouer pour un tour pour x ou o
	 * @param tour : tour du joueur
	 * @param tab : la table de jeu
	 * @return int[][] matrice des x, y de pions adverse
	 */
	int[][] caseAdverse(int tour, int[][] tab) {
		
		int compt = 0;
		// Détermine quel est le numéro du joueur adverse
		int adverse = adversere(tour);
		
		// Première passe : compte le nombre de pions adverses
		for ( int i = 0; i < tab.length; i++){
			for ( int y = 0; y < tab[i].length; y++){
				if ( tab[i][y] == adverse ){
					compt++;
				}
			}
		}
		
		// Crée un tableau de la bonne taille
		int[][] tabAdverse = new int[compt][2];
		
		compt = 0;
		
		// Deuxième passe : remplit le tableau avec les positions
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
		// Crée un tableau de 8 voisins (les 8 directions autour de la case)
		int[][] tabVoisin = new int[8][2];
		
		// Voisins dans les 8 directions : haut, haut-droite, droite, bas-droite, bas, bas-gauche, gauche, haut-gauche
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
	 * @return int[][] matrice de tous les x, y des cases voisines (peut contenir des doublons)
	 */
	int[][] listeCaseVoisin(int[][] tabAdverse) {
		
		int compt = 0;

		// Compte le nombre total de voisins (8 par pion adverse)
		for (int i = 0; i < tabAdverse.length; i++) {
			int[][] rep = caseVoisin(tabAdverse[i][0], tabAdverse[i][1]);
			compt += rep.length;
		}

		// Crée un tableau capable de contenir tous les voisins
		int[][] listeVoisin = new int[compt][2];
		
		compt = 0;

		// Remplit le tableau avec toutes les positions de voisins
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
	 * Verifie si le joueur peut placer un pion sur la case donnee.
	 * Une case est jouable si elle encadre au moins un pion adverse
	 * entre la case testee et un autre pion du joueur dans au moins une direction.
	 * @param int x : ligne de la case
	 * @param int y : colonne de la case
	 * @param int joueur : joueur courant
	 * @param int[][] tab : plateau de jeu
	 * @return boolean : true si la case peut etre jouee
	 */
	boolean encadrePions(int x, int y, int joueur, int[][] tab) {
		boolean estJouable = false;

		// Vérifie d'abord que la case est libre
		if (tab[x][y] == 0) { // case libre
			// Vérifie toutes les directions (8 au total) pour trouver un encadrement valide
			estJouable = (verifieDirection(x, y, -1, 0, joueur, tab) != 0)  // haut
				|| (verifieDirection(x, y, 1, 0, joueur, tab) != 0)   // bas
				|| (verifieDirection(x, y, 0, -1, joueur, tab) != 0)  // gauche
				|| (verifieDirection(x, y, 0, 1, joueur, tab) != 0)   // droite
				|| (verifieDirection(x, y, -1, -1, joueur, tab) != 0) // haut-gauche
				|| (verifieDirection(x, y, -1, 1, joueur, tab) != 0)  // haut-droite
				|| (verifieDirection(x, y, 1, -1, joueur, tab) != 0)  // bas-gauche
				|| (verifieDirection(x, y, 1, 1, joueur, tab) != 0);  // bas-droite
		}

		return estJouable;
	}


	/**
	 * Verifie, dans une direction donnee, si la case encadre un ou plusieurs pions adverses.
	 * Renvoie le nombre de pions adverses encadres si l'encadrement est valide,
	 * sinon renvoie 0.
	 * @param int x : ligne de la case
	 * @param int y : colonne de la case
	 * @param int directionX : direction horizontale (-1,0,1)
	 * @param int directionY : direction verticale (-1,0,1)
	 * @param int joueur : joueur courant
	 * @param int[][] tab : plateau de jeu
	 * @return int : nombre de pions encadres
	 */
	int verifieDirection(int x, int y, int directionX, int directionY, int joueur, int[][] tab) {
		int adverse = adversere(joueur);
		// Commence à la case adjacente dans la direction donnée
		int i = x + directionX;
		int j = y + directionY;
		int nbPionsEncadres = 0;
		boolean valide = false;
		boolean continuer = true;

		// Parcourt la ligne dans la direction jusqu'à trouver un pion du joueur ou sortir du plateau
		while (i >= 0 && i < tab.length && j >= 0 && j < tab.length && continuer) {
			if (tab[i][j] == adverse) {
				nbPionsEncadres++; // on compte les pions adverses
			} else if (tab[i][j] == joueur) {
				// Encadrement validé seulement si on a compté au moins un pion adverse
				if (nbPionsEncadres > 0) {
					valide = true; // encadrement valide
				}
				continuer = false;
			} else { // case vide
				continuer = false;
			}

			i += directionX;
			j += directionY;
		}

		// Si l'encadrement n'est pas valide, on renvoie 0
		if (!valide) {
			nbPionsEncadres = 0; // si pas valide, on remet a 0
		}

		return nbPionsEncadres;
	}

	
	/**
	 * Supprime les doublons dans les cases jouables
	 * @param int[][] caseJouable : tableau des cases jouables
	 * @return int[][] : tableau sans doublons
	 */
	
	int[][] supprimeDoublons(int[][] caseJouable) {
		int compteurUnique = 0;
		// Première passe : compte les cases uniques
		for (int i = 0; i < caseJouable.length; i++){
			boolean diff = true;
			for (int z = i-1; z >= 0; z--){
				if ( caseJouable[i][0] == caseJouable[z][0] && caseJouable[i][1] == caseJouable[z][1]){
					diff = false;
				}
			}
			if (diff) compteurUnique++;
		}
		
		// Crée un nouveau tableau de la taille nécessaire
		int[][] caseJouableSansDoublons = new int [compteurUnique][2];
		
		compteurUnique = 0;
		// Deuxième passe : remplit le tableau avec les cases uniques
		for (int i = 0; i < caseJouable.length; i++){
			boolean diff = true;
			for (int z = i-1; z >= 0; z--){
				if ( caseJouable[i][0] == caseJouable[z][0] && caseJouable[i][1] == caseJouable[z][1]){
					diff = false;
				}
			}
			if (diff){
				caseJouableSansDoublons[compteurUnique] = caseJouable[i];
				compteurUnique++;
			}
		}
	 return caseJouableSansDoublons;
	 }
	
	/**
	 * Verifie quelles cases sont reellement jouables pour le joueur.
	 * Une case est jouable si elle est vide et encadre au moins un pion adverse.
	 * @param tabVoisin : liste des voisins de pions adverses
	 * @param tabJeu : plateau de jeu
	 * @param joueur : joueur en cours (1 ou 2)
	 * @return int[][] : matrice des cases jouables
	 */
	int[][] verifiCaseVoisin(int[][] tabVoisin, int[][] tabJeu, int joueur) {
		int compt = 0;

		// Première passe : compte les cases jouables
		for (int i = 0; i < tabVoisin.length; i++) {
			int x = tabVoisin[i][0];
			int y = tabVoisin[i][1];
			// Vérifie que la case est dans le plateau, vide et encadre des pions
			if (x >= 0 && x < tabJeu.length && y >= 0 && y < tabJeu.length) {
				if (tabJeu[x][y] == 0 && encadrePions(x, y, joueur, tabJeu)) {
					compt++;
				}
			}
		}

		int[][] caseJouable = new int[compt][2];
		compt = 0;

		// Deuxième passe : remplit le tableau avec les cases jouables
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

		// Élimine les doublons avant de retourner le résultat
		return supprimeDoublons(caseJouable);
	}

	/**
	 * Renvoie la liste des cases jouables ayant le maximum de pions retournes.
	 * Utilise verifieDirection() pour calculer les points de chaque coup.
	 * @param int[][] casesJouables : cases jouables
	 * @param int[][] tab : plateau de jeu
	 * @param int joueur : joueur courant
	 * @return int[][] : tableau des meilleures cases
	 */
	int[][] meilleuresCases(int[][] casesJouables, int[][] tab, int joueur) {
		int[] totalPoints = new int[casesJouables.length];
		int max = 0;

		// Calcule le score (nombre de pions retournés) pour chaque case jouable
		for (int i = 0; i < casesJouables.length; i++) {
			int x = casesJouables[i][0];
			int y = casesJouables[i][1];

			// Somme les pions retournés dans toutes les directions
			int score = 0;
			score += verifieDirection(x, y, -1, 0, joueur, tab);
			score += verifieDirection(x, y, 1, 0, joueur, tab);
			score += verifieDirection(x, y, 0, -1, joueur, tab);
			score += verifieDirection(x, y, 0, 1, joueur, tab);
			score += verifieDirection(x, y, -1, -1, joueur, tab);
			score += verifieDirection(x, y, -1, 1, joueur, tab);
			score += verifieDirection(x, y, 1, -1, joueur, tab);
			score += verifieDirection(x, y, 1, 1, joueur, tab);

			totalPoints[i] = score;
			if (score > max) max = score;
		}

		// Compte combien de cases ont le score maximum
		int compteur = 0;
		for (int i = 0; i < totalPoints.length; i++) {
			if (totalPoints[i] == max) compteur++;
		}

		int[][] meilleures = new int[compteur][2];
		int index = 0;

		// Remplit le tableau avec uniquement les cases ayant le meilleur score
		for (int i = 0; i < totalPoints.length; i++) {
			if (totalPoints[i] == max) {
				meilleures[index][0] = casesJouables[i][0];
				meilleures[index][1] = casesJouables[i][1];
				index++;
			}
		}

		return meilleures;
	}


// Methode qui gere tout ce qui est placement de la case ------------------------------------------------------------------------------------------
	
	/**
	 * Place un pion sur le plateau
	 * @param int x : ligne
	 * @param int y : colonne
	 * @param int[][] tab : plateau de jeu
	 * @param int joueur : joueur courant
	 */
	 void placeCase(int x, int y, int[][] tab, int joueur){
		 // Modifie directement la valeur dans le tableau
		 tab[x][y] = joueur;
	 }
	
	/**
	* Montre quelles cases ont la possibilite d'etre joue 
	* puis demande d'en choisir une
	* @param int[][] casePossibleAJouer : cases jouables
	* @param int joueur : joueur courant
	* @return int[] : position choisie [x,y] 
	*/
	int[] reponsesCaseJoue(int[][] casePossibleAJouer, int joueur) {
		String joueurTexte = "";
		
		// Convertit le numéro du joueur en lettre (O ou X)
		if (joueur == 1) joueurTexte = "O";
		else joueurTexte = "X";
		
		System.out.println("Au tour des : " + joueurTexte);
		// Affiche toutes les cases jouables (avec indices +1 pour l'utilisateur)
		System.out.println("Voici les cases possibles a jouer (ligne, colonne) : ");
		for (int i = 0; i < casePossibleAJouer.length; i++) {
			System.out.println((casePossibleAJouer[i][0] + 1) + ", " + (casePossibleAJouer[i][1] + 1));
		}

		// Boucle jusqu'à ce qu'une case valide soit saisie
		while (true) {
			int reponseLigne = SimpleInput.getInt("Numero de ligne : ");
			int reponseCase = SimpleInput.getInt("Numero de colonne : ");

			int[] reponse = {reponseLigne - 1, reponseCase - 1};

			// Vérifie si la case saisie fait partie des cases jouables
			for (int i = 0; i < casePossibleAJouer.length; i++) {
				if (casePossibleAJouer[i][0] == reponse[0] && casePossibleAJouer[i][1] == reponse[1]) {
					return reponse;
				}
			}

			System.out.println("Case invalide, veuillez en choisir une parmi la liste affichee.");
		}
	}
	
// Methode qui gere tout ce qui est retournement de la case ------------------------------------------------------------------------------------------
	
	/**
	 * Retourne les pions adverses dans une direction donnee
	 * @param x : position ligne de la case jouee
	 * @param y : position colonne de la case jouee
	 * @param directionX : direction en x (-1, 0, ou 1)
	 * @param directionY : direction en y (-1, 0, ou 1)
	 * @param joueur : numero du joueur (1 ou 2)
	 * @param tab : plateau de jeu
	 */
	void retourneDansDirection(int x, int y, int directionX, int directionY, int joueur, int[][] tab){
		
		int adverse = adversere(joueur);
		
		// Commence à la case adjacente dans la direction donnée
		int i = x + directionX;
		int j = y + directionY;
		
		boolean continuer = true; 
		
		// Avance dans la direction et retourne tous les pions adverses
		while (i >= 0 && i < tab.length && j >= 0 && j < tab.length && continuer){
			if (tab[i][j] == adverse){
				// Retourne le pion adverse en pion du joueur
				tab[i][j] = joueur;
				i += directionX;
				j += directionY;
			}else{
				continuer = false;
			}
		}
	}
	
	/**
	 * Retourne les pions adverses dans une direction donnee
	 * @param int x : position ligne de la case jouee
	 * @param int y : position colonne de la case jouee
	 * @param int joueur : numero du joueur (1 ou 2)
	 * @param int[][] tab : plateau de jeu
	 */
	void retournePions(int x, int y, int joueur, int[][] tab){
		// Vérifie et retourne dans chaque direction si des pions sont encadrés
		if (verifieDirection(x, y, -1, 0, joueur, tab) != 0) retourneDansDirection(x, y, -1, 0, joueur, tab);
		if (verifieDirection(x, y, 1, 0, joueur, tab) != 0) retourneDansDirection(x, y, 1, 0, joueur, tab);
		if (verifieDirection(x, y, 0, -1, joueur, tab) != 0) retourneDansDirection(x, y, 0, -1, joueur, tab);
		if (verifieDirection(x, y, 0, 1, joueur, tab) != 0) retourneDansDirection(x, y, 0, 1, joueur, tab);
		if (verifieDirection(x, y, -1, -1, joueur, tab) != 0) retourneDansDirection(x, y, -1, -1, joueur, tab);
		if (verifieDirection(x, y, -1, 1, joueur, tab) != 0) retourneDansDirection(x, y, -1, 1, joueur, tab);
		if (verifieDirection(x, y, 1, -1, joueur, tab) != 0) retourneDansDirection(x, y, 1, -1, joueur, tab);
		if (verifieDirection(x, y, 1, 1, joueur, tab) != 0) retourneDansDirection(x, y, 1, 1, joueur, tab);
		
	}
	
	/**
	 * Renvoie le numero du joueur adverse
	 * @param int joueur : joueur courant
	 * @return int : numero du joueur adverse
	 */
	int adversere (int joueur){
		int adversere = 0;
		// Alterne entre joueur 1 et joueur 2
		if (joueur == 1){
			adversere = 2;
		}else{
			adversere = 1;
		}
		return adversere;
	}
	
// Methode qui gere les conditions d'arret du jeu -----------------------------------------------------------------------------------------------
	
	/**
	 * Verifie si le plateau n'est pas plein
	 * @param int[][] tab : plateau de jeu
	 * @return boolean : true si le plateau contient encore des cases libres
	 */
	boolean tabEstPasPlein(int[][] tab){
		boolean estPlein = true;
		// Parcourt tout le plateau pour chercher une case vide (0)
		for ( int i = 0; i < tab.length; i++){
			for ( int y = 0; y < tab[i].length; y++){
				if ( tab[i][y] == 0 ) {
					estPlein = false;
				}
			}
		}
		// Inverse le résultat car la fonction vérifie si le plateau n'est PAS plein
		return !estPlein;
	}
	
// Methode qui gere affichage et creations du plateaux ------------------------------------------------------------------------------------------
	
	/**
	 * Creer un matrice en size * size pour le jeu d'Othello
	 * @param int size : taille de notre matrice 
	 * @return tab : matrice en size*size
	 */
	int[][] tabJeu(int size){
		// Valide que la taille est entre 4 et 16 et paire
		while (!(size >= 4 && size <= 16 && size % 2 == 0)){
			size = SimpleInput.getInt("Nombre entre 4 et 16 pair pour la taille du plateaux de jeu");
		}
		// Crée un plateau carré vide (initialisé avec des 0)
		int[][] tab = new int [size][size];
		return tab;
	}
	
	/**
	 * Affiche un tableau/matice en size * size pour le jeu d'Othello
	 * @param int[][] tab : le plateau a afficher
	 */
	void afficheTabJeu (int[][] tab){
		
		System.out.print("\n");
		System.out.print("\t   ");
		
		// Affiche les numéros de colonnes en haut
		for (int i = 0; i < tab.length; i++){
			if ( i < 9){
				System.out.print("  " + (i+1) );
			}else{
				System.out.print(" " + (i+1) );
			}
		}
		
		System.out.print("\n");
		
		// Affiche chaque ligne avec son numéro et son contenu
		for (int i = 0; i < tab.length; i++){
			if ( i < 9){
				System.out.print("\t" + (i+1) +"  ");
			}else{
				System.out.print("\t" + (i+1) +" ");
			}
			
			// Affiche le contenu de chaque case : vide, o, ou x
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
