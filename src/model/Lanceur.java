package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;



public class Lanceur {

	public static Noeud monArbre = new Noeud();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		lireFichierDon("src/mesFichiers/STAGIAIRES.DON");
		
		try {
			RandomAccessFile raf = new RandomAccessFile("src/mesFichiers/STAGIAIRES.bin", "rw");
			
			int tailleObjet = 16 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
			int nbObjetEcrit = (int) raf.length() / tailleObjet;
			for (int i=0;i<nbObjetEcrit;i++) {
				String nom = "";
				for(int j = 0; j < Stagiaire.TAILLE_MAX_NOM_PRENOM; j++) {
					nom += raf.readChar();
				}
				String prenom = "";
				for(int j = 0; j < Stagiaire.TAILLE_MAX_NOM_PRENOM; j++) {
					prenom += raf.readChar();
				}
				String departement = "";
				for(int j = 0; j < Stagiaire.TAILLE_MAX_DEP_PROMO; j++) {
					departement += raf.readChar();
				}
				String promo = "";
				for(int j = 0; j < Stagiaire.TAILLE_MAX_DEP_PROMO; j++) {
					promo += raf.readChar();
				}
				int annee = raf.readInt();
				int filsG = raf.readInt();
				int filsD = raf.readInt();
				int doublon = raf.readInt();
				nom = nom.trim();
				System.out.println(nom+" filsG "+filsG+" filsD "+filsD+" doublon "+doublon);
			}
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<Stagiaire> listeDeRecherche = new ArrayList<>();
		Lanceur.monArbre.rechercherParDepartement("76", listeDeRecherche, "src/mesFichiers/STAGIAIRES.bin", 0);
		for (Stagiaire stagiaire : listeDeRecherche) {
			System.out.println(stagiaire);
		}
		System.out.println("----------");
		listeDeRecherche.clear();
		Lanceur.monArbre.rechercherParNom("UNG", listeDeRecherche, "src/mesFichiers/STAGIAIRES.bin", 0);
		for (Stagiaire stagiaire : listeDeRecherche) {
			System.out.println(stagiaire);
		}
		System.out.println("----------");
		listeDeRecherche.clear();
		Lanceur.monArbre.rechercherParPrenom("Brahim", listeDeRecherche, "src/mesFichiers/STAGIAIRES.bin", 0);
		for (Stagiaire stagiaire : listeDeRecherche) {
			System.out.println(stagiaire);
		}
		System.out.println("----------");
		listeDeRecherche.clear();
		Lanceur.monArbre.rechercherParPromo("ATOD 22", listeDeRecherche, "src/mesFichiers/STAGIAIRES.bin", 0);
		for (Stagiaire stagiaire : listeDeRecherche) {
			System.out.println(stagiaire);
		}
		System.out.println("----------");
		listeDeRecherche.clear();
		Lanceur.monArbre.rechercherParAnnee(2014, listeDeRecherche, "src/mesFichiers/STAGIAIRES.bin", 0);
		for (Stagiaire stagiaire : listeDeRecherche) {
			System.out.println(stagiaire);
		}
		System.out.println("----------");
		Stagiaire ss  = new Stagiaire("POTIN","Thomas","75","ATOD 21",2014);
		List<Stagiaire> s=new ArrayList<>();
		try {
		s=monArbre.convertirFichierBinToList("src/mesFichiers/STAGIAIRES.bin",0,s);
		s=monArbre.supprimerStagiaire(s, ss);
		for(Stagiaire i:s) {
			System.out.println(i.toString());
			
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		monArbre.construireNvFichierBin(s,"src/mesFichiers/STAGIAIRES.bin");
		Stagiaire ss1  = new Stagiaire("CHAVENEAU","sirine","4","ss",2014);
		List<Stagiaire> s1=new ArrayList<>();
		List<Stagiaire> s3=new ArrayList<>();
		try {
			Lanceur.monArbre.modifier(ss1,"src/mesFichiers/STAGIAIRES.bin",0 ,s1,s3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			RandomAccessFile raf = new RandomAccessFile("src/mesFichiers/STAGIAIRES.bin", "rw");
			
			int tailleObjet = 16 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
			int nbObjetEcrit = (int) raf.length() / tailleObjet;
			for (int i=0;i<nbObjetEcrit;i++) {
				String nom = "";
				for(int j = 0; j < Stagiaire.TAILLE_MAX_NOM_PRENOM; j++) {
					nom += raf.readChar();
				}
				String prenom = "";
				for(int j = 0; j < Stagiaire.TAILLE_MAX_NOM_PRENOM; j++) {
					prenom += raf.readChar();
				}
				String departement = "";
				for(int j = 0; j < Stagiaire.TAILLE_MAX_DEP_PROMO; j++) {
					departement += raf.readChar();
				}
				String promo = "";
				for(int j = 0; j < Stagiaire.TAILLE_MAX_DEP_PROMO; j++) {
					promo += raf.readChar();
				}
				int annee = raf.readInt();
				int filsG = raf.readInt();
				int filsD = raf.readInt();
				int doublon = raf.readInt();
				nom = nom.trim();
				System.out.println(nom+" filsG "+filsG+" filsD "+filsD+" doublon "+doublon);
			}
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void lireFichierDon(String cheminAccesFichierDon) {
		
		
		try {
			FileReader	fr = new FileReader(cheminAccesFichierDon);
			
			BufferedReader br = new BufferedReader(fr);
			
			while(br.ready()) {
				
				String nom = br.readLine();
				String prenom = br.readLine();
				String departement =  br.readLine();
				String promo = br.readLine();
				int annee = Integer.parseInt(br.readLine());
				monArbre.ajouterUnStagiaire(new Stagiaire(nom,prenom,departement,promo,annee),"src/mesFichiers/STAGIAIRES.bin");
				br.readLine();
							
			}
			

			
			br.close();
			fr.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			

	}
	

}


	