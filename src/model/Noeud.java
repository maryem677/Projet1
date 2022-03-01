package model;

 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;





public class Noeud {
	
	private Stagiaire stagiaire;
	private int filsDroit;
	private int filsGauche;
	private int doublon;
	
	
	
	public Noeud(Stagiaire stagiaire, int filsGauche, int filsDroit, int doublon) {
		super();
		this.stagiaire = stagiaire;
		this.filsGauche = filsGauche;
		this.filsDroit = filsDroit;
		this.doublon = doublon;
	}
	
	

	
	public Noeud() {
		super();
	}




	public Stagiaire getStagiaire() {
		return stagiaire;
	}


	public void setStagiaire(Stagiaire stagiaire) {
		this.stagiaire = stagiaire;
	}


	public int getFilsDroit() {
		return filsDroit;
	}


	public void setFilsDroit(int filsDroit) {
		this.filsDroit = filsDroit;
	}


	public int getFilsGauche() {
		return filsGauche;
	}


	public void setFilsGauche(int filsGauche) {
		this.filsGauche = filsGauche;
	}

	
	public int getDoublon() {
		return doublon;
	}




	public void setDoublon(int doublon) {
		this.doublon = doublon;
	}

	@Override
	public String toString() {
		return "";
	}	
	
	
	public Noeud lireUnNoeud(RandomAccessFile raf) {
		Noeud noeud = new Noeud();
		try {
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
			nom = nom.trim();
			prenom = prenom.trim();
			departement = departement.trim();
			promo = promo.trim();
			
			int filsGauche = raf.readInt();
			int filsDroit = raf.readInt();
			int doublon = raf.readInt();
			noeud.stagiaire = new Stagiaire(nom,prenom,departement,promo,annee);
			noeud.filsGauche= filsGauche;
			noeud.filsDroit = filsDroit;
			noeud.doublon = doublon;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return noeud;
	}

	
	
	public void ajouterUnStagiaire(Stagiaire stagiaire, String cheminAccesFichierBin) throws IOException {
			
		RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");	
			
			
			
			if (raf.length()==0) {
				raf.writeChars(stagiaire.nomLong()+stagiaire.prenomLong()+stagiaire.departementLong()+stagiaire.promoLong());
				raf.writeInt(stagiaire.getAnnee());
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
			} else {
				Noeud courant = new Noeud();
				courant = lireUnNoeud(raf);
				ajouterUnStagiaireRecursive(stagiaire, courant, raf);
			}
			raf.close();
	}
	
	public void ajouterUnStagiaireRecursive(Stagiaire stagiaire, Noeud courant, RandomAccessFile raf) throws IOException {
		
				
				
		// taille en octet d'un objet Stagiaire ecrit en binaire
		int tailleObjet = 16 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
							

				
		// lecture du 1er nom ecrit dans le fichier
		if (courant.stagiaire.getNom().compareTo(stagiaire.getNom()) > 0) {
			if (courant.filsGauche==-1) {
				raf.seek(raf.getFilePointer()-12);
				raf.writeInt((int) raf.length()/tailleObjet);
				raf.seek(raf.length());
				raf.writeChars(stagiaire.nomLong()+stagiaire.prenomLong()+stagiaire.departementLong()+stagiaire.promoLong());
				raf.writeInt(stagiaire.getAnnee());
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
			} else {
				raf.seek(courant.filsGauche*tailleObjet);
				ajouterUnStagiaireRecursive(stagiaire, lireUnNoeud(raf), raf);					
			}
		} else if (courant.stagiaire.getNom().compareTo(stagiaire.getNom()) < 0) {
			if (courant.filsDroit==-1) {
				raf.seek(raf.getFilePointer()-8);
				raf.writeInt((int) raf.length()/tailleObjet);
				raf.seek(raf.length());
				raf.writeChars(stagiaire.nomLong()+stagiaire.prenomLong()+stagiaire.departementLong()+stagiaire.promoLong());
				raf.writeInt(stagiaire.getAnnee());
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
			} else {
				raf.seek(courant.filsDroit*tailleObjet);
				ajouterUnStagiaireRecursive(stagiaire, lireUnNoeud(raf), raf);
			}
		} else {
			if (courant.doublon==-1) {
				raf.seek(raf.getFilePointer()-4);
				raf.writeInt((int) raf.length()/tailleObjet);
				raf.seek(raf.length());
				raf.writeChars(stagiaire.nomLong()+stagiaire.prenomLong()+stagiaire.departementLong()+stagiaire.promoLong());
				raf.writeInt(stagiaire.getAnnee());
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
			} else {
				raf.seek(courant.doublon*tailleObjet);
				ajouterUnStagiaireRecursive(stagiaire, lireUnNoeud(raf), raf);	
			}
		}
	}


public List<Stagiaire> rechercherParDepartement(String DepartementRecherche,List <Stagiaire>list, String cheminAccesFichierBin, long position){
		
		// taille en octet d'un objet Stagiaire ecrit en binaire
		//	int tailleObjet = 12 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
				
			try {
				RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");
				raf.seek(position);
				
					Noeud courant = new Noeud();
					courant = lireUnNoeud(raf);
					
					if (courant.stagiaire.getDepartement().equals(DepartementRecherche)) {
						
				    list.add(courant.stagiaire);
					}
					
					
					if (raf.getFilePointer()!=raf.length()){
						position = raf.getFilePointer();
					courant.rechercherParDepartement(DepartementRecherche, list, cheminAccesFichierBin,position);
						
					}
					raf.close();
					
					
			}
			
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
			
  }

public List<Stagiaire> rechercherParAnnee(int anneeRecherche, List<Stagiaire> list, String cheminAccesFichierBin, long position) {
	
	try {
		RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");
		raf.seek(position);
			
			
			Noeud courant = new Noeud();
			courant = lireUnNoeud(raf);
			
			if (courant.stagiaire.getAnnee()==anneeRecherche) {
				
		    list.add(courant.stagiaire);
			}
			
			
			if (raf.getFilePointer()!=raf.length()){
				position = raf.getFilePointer();
			courant.rechercherParAnnee(anneeRecherche, list, cheminAccesFichierBin, position);
				
			}
			raf.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return list;
}

public List<Stagiaire> rechercherParPromo(String PromoRecherche,List <Stagiaire>list, String cheminAccesFichierBin, long position){
	
	// taille en octet d'un objet Stagiaire ecrit en binaire
	//	int tailleObjet = 12 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
			
		try {
			RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");
			raf.seek(position);
			
			Noeud courant = new Noeud();
			courant = lireUnNoeud(raf);
				
				if (courant.stagiaire.getPromo().equals(PromoRecherche)) {
					
			    list.add(courant.stagiaire);
				}
				
				
				if (raf.getFilePointer()!=raf.length()){
					position = raf.getFilePointer();
				courant.rechercherParPromo(PromoRecherche, list, cheminAccesFichierBin,position);
					
				}
				raf.close();
				
		}
		
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
}

public List<Stagiaire> rechercherParNom(String nomRecherche,List <Stagiaire>list, String cheminAccesFichierBin, long position){
    // taille en octet d'un objet Stagiaire ecrit en binaire
    //  int tailleObjet = 12 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
        try {
            RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");
            raf.seek(position);
			Noeud courant = new Noeud();
			courant = lireUnNoeud(raf);
			if (courant.stagiaire.getNom().equals(nomRecherche)) {
                list.add(courant.stagiaire);
                }
                if (raf.getFilePointer()!=raf.length()){
                    position = raf.getFilePointer();
                courant.rechercherParNom(nomRecherche, list, cheminAccesFichierBin,position);
                }
                raf.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
	}

public List<Stagiaire> rechercherParPrenom(String prenomRecherche,List <Stagiaire>list, String cheminAccesFichierBin, long position){
    // taille en octet d'un objet Stagiaire ecrit en binaire
    //  int tailleObjet = 12 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
        try {
            RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");
            raf.seek(position);
			Noeud courant = new Noeud();
			courant = lireUnNoeud(raf);
                if (courant.stagiaire.getPrenom().equals(prenomRecherche)) {
                list.add(courant.stagiaire);
                }
                if (raf.getFilePointer()!=raf.length()){
                    position = raf.getFilePointer();
                courant.rechercherParPrenom(prenomRecherche, list, cheminAccesFichierBin,position);
                }
                raf.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
}

/* public List<Stagiaire> rechercherMulticrit(Stagiaire,List <Stagiaire>list, String cheminAccesFichierBin, long position){
	
	// taille en octet d'un objet Stagiaire ecrit en binaire
	//	int tailleObjet = 12 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
			
		try {
			RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");
			raf.seek(position);
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
				nom = nom.trim();
				prenom = prenom.trim();
				departement = departement.trim();
				promo = promo.trim();
				
				int filsGauche = raf.readInt();
				int filsDroit = raf.readInt();
				
				Noeud courant = new Noeud(new Stagiaire(nom,prenom,departement,promo,annee),filsGauche,filsDroit);
				
				
				if () {
					
			    list.add(courant.stagiaire);
				}
				
				
				if (raf.getFilePointer()!=raf.length()){
					position = raf.getFilePointer();
				courant.rechercherMulticrit(listeCritere, list, cheminAccesFichierBin,position);
					
				}
				raf.close();
				
				
		}
		
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
}*/



	public List<Stagiaire> convertirFichierBinToList(String cheminAccesFichierBin,long position,List <Stagiaire>list) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");
		raf.seek(position);
		Noeud courant = new Noeud();
		courant = lireUnNoeud(raf);
	
		Stagiaire s = new Stagiaire(courant.stagiaire.getNom(), courant.stagiaire.getPrenom(), courant.stagiaire.getDepartement(), courant.stagiaire.getPromo(),courant.stagiaire.getAnnee());
		list.add(s);
		
		if ((raf.getFilePointer() != raf.length())) {

			position = raf.getFilePointer();
			raf.close();
			return convertirFichierBinToList( cheminAccesFichierBin, position,list);
		
		}else {
			raf.close();
		return list;
		
	}}
	public List<Stagiaire> supprimerStagiaire(List<Stagiaire>list,Stagiaire stagiaireAsupprimer){
		
		
				list.remove(stagiaireAsupprimer);
				return list;
	}
	public void construireNvFichierBin(List<Stagiaire>list,String cheminAccesFichierBin) {
		try {
		RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");
		raf.setLength(0);
		for(Stagiaire i : list) {
		String	nom=i.getNom();
		String prenom=i.getPrenom();
		String departement=i.getDepartement();
		String promo=i.getPromo();
		int annee=i.getAnnee();
		
			
			
			raf.seek(0);

			ajouterUnStagiaire(new Stagiaire(nom,prenom,departement,promo,annee), cheminAccesFichierBin);
		
		}} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		}
	public void supprimer (String cheminAccesFichierBin,long position,List<Stagiaire>list,Stagiaire stagiaireAsupp ) throws IOException {
	
			convertirFichierBinToList(cheminAccesFichierBin, position,list);
	
			// TODO Auto-generated catch block
		
		supprimerStagiaire(list, stagiaireAsupp);
		construireNvFichierBin(list, cheminAccesFichierBin);
		
	}
	public void modifier(Stagiaire stagiaireAmodifier,String cheminAccesFichierBin,long position,List<Stagiaire>list1,List<Stagiaire>list2 ) throws IOException {
	//	List<Stagiaire> list1 =new ArrayList<>();
		rechercherParNom(stagiaireAmodifier.getNom(), list1, cheminAccesFichierBin, 0);
		supprimer(cheminAccesFichierBin,position,list2,list1.get(0));

		try {
			this.ajouterUnStagiaire(stagiaireAmodifier, cheminAccesFichierBin);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	}

	


