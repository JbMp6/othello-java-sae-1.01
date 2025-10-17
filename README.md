## 📋 README - OTHELLO 1v1 (Deux Joueurs au Clavier)

### Description
Jeu d'Othello (Reversi) en Java pour **deux joueurs humains** qui jouent alternativement sur le même clavier. Le but est d'avoir plus de pions de sa couleur que l'adversaire en encadrant les pions adverses.

### Installation
```
Compilez: javac Othello.java
Exécutez: java Othello
```

### Règles
- Les deux joueurs placent alternativement leurs pions
- Un pion ne peut être placé que s'il encadre au moins un pion adverse entre lui et un autre pion du joueur
- Les pions encadrés doivent être retournés à la couleur du joueur
- Si un joueur ne peut pas jouer, il passe son tour automatiquement
- La partie s'arrête quand aucun des deux joueurs ne peut jouer
- Le joueur avec le plus de pions gagne

### Contrôles
- **Joueur 1 (o)** : Joue en premier
- **Joueur 2 (x)** : Joue en deuxième
- À chaque tour : entrez le numéro de ligne (1-8) puis le numéro de colonne (1-8)
- Symboles: `o` = Joueur 1 | `x` = Joueur 2 | espace = Case vide

---

## 🔧 TÂCHES À FAIRE POUR UN JEU 1V1 FONCTIONNEL

### Priorité CRITIQUE (Jeu non fonctionnel sans)

#### 1. Implémenter le retournement de pions ⭐ URGENT
**Fichier :** `Othello.java`  
**À créer :** Deux nouvelles méthodes
- `retournePions(int x, int y, int joueur, int[][] tab)` : retourne tous les pions adverses encadrés dans les 8 directions après placement
- `retourneDansDirection(int x, int y, int dx, int dy, int joueur, int adverse, int[][] tab)` : retourne les pions dans une seule direction

**À modifier :** Méthode `demarreJeu()` - appeler `retournePions()` après `placeCase()`

---

#### 2. Remplir la boucle de jeu correctement
**Fichier :** `Othello.java`  
**À modifier :** Méthode `demarreJeu()`
- Remplacer la boucle `for (9 tours)` par une boucle `while` qui tourne tant que le jeu n'est pas fini
- Ajouter une variable pour compter les passages consécutifs (si 2 joueurs passent d'affilée = fin de partie)
- Pour chaque tour : vérifier s'il y a des coups possibles
  - Si oui : le joueur joue
  - Si non : le joueur passe son tour et afficher un message
- Passer au joueur suivant

---

#### 3. Créer la méthode d'affichage final avec scores
**Fichier :** `Othello.java`  
**À créer :** Méthode `afficheFin(int[][] tab)`
- Afficher le plateau final
- Compter les pions de chaque joueur
- Afficher les scores des deux joueurs
- Afficher le vainqueur (ou égalité)
- Afficher un message de fin de partie

---

#### 4. Créer la méthode de comptage de points
**Fichier :** `Othello.java`  
**À créer :** Méthode `comptePoints(int[][] tab)`
- Parcourir le plateau
- Compter les pions du joueur 1 et du joueur 2
- Retourner les deux scores dans un tableau `int[2]`

---

### Priorité HAUTE (Pour une meilleure expérience)

#### 5. Afficher le score après chaque coup
**Fichier :** `Othello.java`  
**À modifier :** Méthode `demarreJeu()`
- Après que le joueur place un pion, afficher les scores actuels
- Afficher la position où le pion a été placé

---

#### 6. Améliorer les messages pour les joueurs
**Fichier :** `Othello.java`  
**À modifier :** Méthode `demarreJeu()`
- Ajouter un message clair indiquant quel joueur doit jouer
- Afficher le nombre de coups possibles disponibles
- Afficher un message quand un joueur passe son tour

---

#### 7. Nettoyer les doublons dans la liste des cases voisines
**Fichier :** `Othello.java`  
**À modifier :** Méthode `listeCaseVoisin()`
- Le tableau retourné peut contenir des doublons (cas où deux pions adverses sont voisins)
- Utiliser un `Set` pour supprimer les doublons
- Retourner un tableau sans doublons

---

### Priorité BASSE (Extras)

#### 8. Option de rejouer la partie
**Fichier :** `Othello.java`  
**À modifier :** Méthode `principal()`
- Ajouter une boucle pour permettre de rejouer plusieurs parties
- Demander au joueur s'il veut rejouer après chaque partie
- Recommencer depuis le début si oui

---

## 📊 RÉSUMÉ DES TÂCHES

| Priorité | Tâche | Délai estimé |
|----------|-------|--------------|
| 🔴 CRITIQUE | Retournement de pions | 1h |
| 🔴 CRITIQUE | Boucle de jeu avec passages | 1h |
| 🔴 CRITIQUE | Affichage final + comptage points | 30 min |
| 🟡 HAUTE | Afficher scores intermédiaires | 15 min |
| 🟡 HAUTE | Messages amélorés | 15 min |
| 🟡 HAUTE | Nettoyer doublons | 20 min |
| 🟢 BASSE | Option rejouer | 15 min |

---

## ✅ CHECKLIST D'IMPLÉMENTATION

- [X] Créer méthodes de retournement de pions
- [X] Modifier `demarreJeu()` avec boucle while
- [X] Créer `afficheFin()` et `comptePoints()`
- [X] Tester une partie complète du début à la fin
- [X] Ajouter affichage des scores intermédiaires
- [ ] Améliorer les messages des joueurs
- [X] Nettoyer les doublons
- [X] Tester toutes les situations de passage de tour
- [ ] Ajouter option rejouer (optionnel)
