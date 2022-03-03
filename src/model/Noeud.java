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
		return "Noeud [stagiaire=" + stagiaire + ", filsDroit=" + filsDroit + ", filsGauche=" + filsGauche
				+ ", doublon=" + doublon + "]";
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

	public void ecrireUnNoeud(RandomAccessFile raf, Noeud courant) throws IOException {
		raf.writeChars(courant.stagiaire.nomLong()+courant.stagiaire.prenomLong()+courant.stagiaire.departementLong()+courant.stagiaire.promoLong());
		raf.writeInt(courant.stagiaire.getAnnee());
		raf.writeInt(courant.getFilsGauche());
		raf.writeInt(courant.getFilsDroit());
		raf.writeInt(courant.getDoublon());
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


	public List<Stagiaire> parcoursInfixe(List<Stagiaire> liste, String cheminAccesFichierBin, int position) {
		int tailleObjet = 16 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
		try {
			RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");
			raf.seek(position);
			if (raf.length()!=0) {
				Noeud courant = lireUnNoeud(raf);
				if (courant.filsGauche!=-1 && courant.filsDroit!=-1) {
					parcoursInfixe(liste, cheminAccesFichierBin, courant.filsGauche*tailleObjet);
					liste.add(courant.stagiaire);
					if (courant.doublon!=-1) {
						parcoursInfixe(liste, cheminAccesFichierBin, courant.doublon*tailleObjet);
					}
					parcoursInfixe(liste, cheminAccesFichierBin, courant.filsDroit*tailleObjet);
				} else if (courant.filsGauche!=-1 && courant.filsDroit==-1) {
					parcoursInfixe(liste, cheminAccesFichierBin, courant.filsGauche*tailleObjet);
					liste.add(courant.stagiaire);
					if (courant.doublon!=-1) {
						parcoursInfixe(liste, cheminAccesFichierBin, courant.doublon*tailleObjet);
					}
				} else if (courant.filsGauche==-1 && courant.filsDroit!=-1) {
					liste.add(courant.stagiaire);
					if (courant.doublon!=-1) {
						parcoursInfixe(liste, cheminAccesFichierBin, courant.doublon*tailleObjet);
					}
					parcoursInfixe(liste, cheminAccesFichierBin, courant.filsDroit*tailleObjet);
				} else {
					liste.add(courant.stagiaire);
					if (courant.doublon!=-1) {
						parcoursInfixe(liste, cheminAccesFichierBin, courant.doublon*tailleObjet);
					}
				}
			}
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}
	

	public void rechercherMulticrit(Stagiaire stagiaireRecherche,List<Stagiaire> liste, String cheminAccesFichierBin, int position){
		
		// taille en octet d'un objet Stagiaire ecrit en binaire
		int tailleObjet = 16 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
			

			try {
				RandomAccessFile raf = new RandomAccessFile(cheminAccesFichierBin, "rw");
				raf.seek(position);
				
				if (raf.length()!=0) {
					Noeud courant = lireUnNoeud(raf);
					if (courant.filsGauche!=-1 && courant.filsDroit!=-1) {
						rechercherMulticrit(stagiaireRecherche, liste, cheminAccesFichierBin, courant.filsGauche*tailleObjet);
						if (courant.stagiaire.compareMultiCrit(stagiaireRecherche)) {
							liste.add(courant.stagiaire);
						}
						if (courant.doublon!=-1) {
							rechercherMulticrit(stagiaireRecherche, liste, cheminAccesFichierBin, courant.doublon*tailleObjet);
						}
						rechercherMulticrit(stagiaireRecherche, liste, cheminAccesFichierBin, courant.filsDroit*tailleObjet);
					} else if (courant.filsGauche!=-1 && courant.filsDroit==-1) {
						rechercherMulticrit(stagiaireRecherche, liste, cheminAccesFichierBin, courant.filsGauche*tailleObjet);
						if (courant.stagiaire.compareMultiCrit(stagiaireRecherche)) {
							liste.add(courant.stagiaire);						
						}
						if (courant.doublon!=-1) {
							rechercherMulticrit(stagiaireRecherche, liste, cheminAccesFichierBin, courant.doublon*tailleObjet);
						}
					} else if (courant.filsGauche==-1 && courant.filsDroit!=-1) {
						if (courant.stagiaire.compareMultiCrit(stagiaireRecherche)) {
							liste.add(courant.stagiaire);
						}
						if (courant.doublon!=-1) {
							rechercherMulticrit(stagiaireRecherche, liste, cheminAccesFichierBin, courant.doublon*tailleObjet);
						}
						rechercherMulticrit(stagiaireRecherche, liste, cheminAccesFichierBin, courant.filsDroit*tailleObjet);
					} else {
						if (courant.stagiaire.compareMultiCrit(stagiaireRecherche)) {
							liste.add(courant.stagiaire);
						}
						if (courant.doublon!=-1) {
							rechercherMulticrit(stagiaireRecherche, liste, cheminAccesFichierBin, courant.doublon*tailleObjet);
						}
					}
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

	public void suppressionNoeud(Stagiaire stagiaireAsupprimer, String cheminaFichBin, int position)  {
		try {
			RandomAccessFile raf = new RandomAccessFile(cheminaFichBin, "rw");
			raf.seek(position);
	
			int tailleObjet = 16 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
			Noeud courant = lireUnNoeud(raf);
	
			if (courant.stagiaire.compare(stagiaireAsupprimer)) {
				raf.seek(position);
				supressionRacine(raf, courant);
			} else if (courant.stagiaire.getNom().compareTo(stagiaireAsupprimer.getNom())>0) {
				if (courant.filsGauche!=-1)  {
					suppressionNoeud(stagiaireAsupprimer, cheminaFichBin, courant.filsGauche*tailleObjet);
				}
			} else if (courant.stagiaire.getNom().compareTo(stagiaireAsupprimer.getNom())<0){
				if (courant.filsDroit!=-1) {
					suppressionNoeud(stagiaireAsupprimer, cheminaFichBin, courant.filsDroit*tailleObjet);
				}
			} else {
				if (courant.doublon!=-1) {
					suppressionNoeud(stagiaireAsupprimer, cheminaFichBin, doublon*tailleObjet);
				}

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

	public Noeud noeudSuccesseur(RandomAccessFile raf, Noeud courant){
		Noeud noeudFilD = new Noeud();
		int tailleObjet = 16 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
		try {
			raf.seek(courant.filsDroit*tailleObjet);
			noeudFilD = lireUnNoeud(raf);

			while (noeudFilD.filsGauche != -1) {
				raf.seek(noeudFilD.filsGauche*tailleObjet);
				noeudFilD= lireUnNoeud(raf);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
		return noeudFilD;
	}


	private void supressionRacine(RandomAccessFile raf, Noeud courant) {
		int tailleObjet = 16 + 4 * Stagiaire.TAILLE_MAX_NOM_PRENOM + 4*Stagiaire.TAILLE_MAX_DEP_PROMO;
		try {
			if (courant.filsGauche==-1 && courant.filsDroit==-1 && courant.doublon==-1) {
				int positionRacine = (int) raf.getFilePointer();
				raf.seek(0);
				boolean b = true;
				while (b) {
				
					Noeud nouveauCourant = lireUnNoeud(raf);
					if (nouveauCourant.filsGauche == positionRacine/tailleObjet) {
						raf.seek(raf.getFilePointer()-12);
						raf.writeInt(-1);
						b = false;

					} else if (nouveauCourant.filsDroit == positionRacine/tailleObjet) {
						raf.seek(raf.getFilePointer()-8);
						raf.writeInt(-1);
						b= false;
		
					} else if (nouveauCourant.doublon==positionRacine/tailleObjet) {
						raf.seek(raf.getFilePointer()-4);
						raf.writeInt(-1);
						b = false;
					}
				}
			} else if (courant.filsGauche!=-1 && courant.filsDroit==-1) {
				int positionRacine = (int) raf.getFilePointer();
				raf.seek(courant.filsGauche*tailleObjet);
				Noeud noeudFilG= lireUnNoeud(raf);
				suppressionNoeud(noeudFilG.stagiaire, "src/mesFichiers/STAGIAIRES.bin", positionRacine);
				raf.seek(positionRacine);
				ecrireUnNoeud(raf, noeudFilG);
				raf.seek(raf.getFilePointer()-4);
				raf.writeInt(courant.doublon);
			
			} else if (courant.filsGauche==-1 && courant.filsDroit!=-1) {
				int positionRacine = (int) raf.getFilePointer();
				raf.seek(courant.filsDroit*tailleObjet);
				Noeud noeudFilD= lireUnNoeud(raf);
				suppressionNoeud(noeudFilD.stagiaire, "src/mesFichiers/STAGIAIRES.bin", positionRacine);
				raf.seek(positionRacine);
				ecrireUnNoeud(raf, noeudFilD);
				raf.seek(raf.getFilePointer()-4);
				raf.writeInt(courant.doublon);
			} else if (courant.filsGauche==-1 && courant.filsDroit==-1 && courant.doublon!=-1) {
				int positionRacine = (int) raf.getFilePointer();
				raf.seek(courant.doublon*tailleObjet);
				Noeud noeudDoublon= lireUnNoeud(raf);
				suppressionNoeud(noeudDoublon.stagiaire, "src/mesFichiers/STAGIAIRES.bin", positionRacine);
				raf.seek(positionRacine);
				ecrireUnNoeud(raf,noeudDoublon);
			} else if (courant.filsGauche!=-1 && courant.filsDroit!=-1) {
				int positionRacine = (int) raf.getFilePointer();
				Noeud noeudSuccesseur = noeudSuccesseur(raf, courant);
				suppressionNoeud(noeudSuccesseur.stagiaire, "src/mesFichiers/STAGIAIRES.bin", courant.filsDroit*tailleObjet);
				raf.seek(positionRacine);
				ecrireUnNoeud(raf,noeudSuccesseur);
				raf.seek(raf.getFilePointer()-12);
				raf.writeInt(courant.filsGauche);
				raf.writeInt(courant.filsDroit);
				raf.writeInt(courant.doublon);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}

	public void modifierStagiaire(Stagiaire stagiaireAmodifier, Stagiaire nouveauStagiaire, String cheminFichierBinaire, int position) throws IOException {
		
		List<Stagiaire> listeAModifier =new ArrayList<>();
		rechercherMulticrit(stagiaireAmodifier,listeAModifier, cheminFichierBinaire, position);
		
		Stagiaire  stagiaireRecherche = listeAModifier.get(0) ;
		suppressionNoeud(stagiaireRecherche, cheminFichierBinaire, position);
		
		ajouterUnStagiaire(nouveauStagiaire, cheminFichierBinaire);
		
		
	}
}
