package MuhendislikProjesi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Scanner;

public class Main {
	public void hashAtma() throws IOException
	{
		String [] isim = new String[50];
		int []hash = new int[100];
		int indis=0;
		//Text dosyam�z� okumak i�in text ismimizi yaz�yoruz.
		File file = new File("isim1.txt");
		//dosya okuyor
		BufferedReader reader = new BufferedReader(new FileReader(file));
		//sat�r okunuyor
		String satir = reader.readLine();
		
		//Text dosyam�z sonuna kadar okunur ve
		// isim adl� string diziye at�l�r.
		while(satir != null)
		{
			isim[indis]=satir;
			satir = reader.readLine();
			indis++;
		}
		//Text dosyas�ndan okudu�umuz ve string bir diziye att���m�z
		// isimleri char dizisine d�n��t�r�p ascii kar��l�klar�n� al�p 
		// hash dizisine quadratic mant�k ile koyar.
		for(int i=0; i<isim.length; i++)
		{
			String str;
			str=isim[i];
			char parcala[]= str.toCharArray();
			int toplam=0;
			int modGecici=0;
			for(int j=0; j<parcala.length; j++)
			{
				toplam = toplam+ parcala[j]*(j+1) * (j+1); //ismin ascii karsiligi bulunuyor.
			}
			int mod = toplam%101;
		
			if(mod >= 100)
			{
				mod -=100;
			}
			if( hash[mod] == 0 )
			{
				hash[mod] = toplam;
				toplam = 0;
			}
			else
			{
			//quadratic mantik ile arayip bos buldugu indise ascii karsiligini yazacak.	
				for(int k=0; k<100; k++)
				{
					modGecici = mod + (k+1)*(k+1) %101;
					if(modGecici>=100)
					{
							modGecici -=100;
					}
					if(hash[modGecici] == 0)
					{
						hash[modGecici]=toplam;
						break;
					}	
				}
				
			}
			
		
		}
		
		// hash dizisini ekrana yazd�r�r..
		 
		for(int z=0; z<hash.length;z++)
		{
			System.out.println(z+". indis: "+hash[z]);
		}
		
		
		
		//Text dosyam�zdaki isimleri okuyup hash dizisine quadratic mant�k ile att�ktan sonra 
		//kullan�can isim al�p
		// aramak i�in kelimeGir() fonksiyonuna g�nderiyoruz.
		kelimeGir(hash,isim);

	}
	
	public void kelimeGir(int[]hash3, String[] isim1)
	{
		/*
		 * klavyeden girilen isimi al�yoruz.
		 */
		Scanner scanner = new Scanner(System.in);
		System.out.print("Aranacak Kelimeyi Giriniz: ");
		String okunanDeger = scanner.nextLine();
		
		for(int i=0; i<isim1.length; i++)
		{
			int say=0;
			char girilenParcala[]= okunanDeger.toCharArray();
			int toplam=0,modArama=0,modGecici=0;
			
			//kullan�c�n�n girdi�i kelimenin toplam ascii de�erini hesapl�yoruz.
			for(int j=0; j<girilenParcala.length; j++)
			{
				toplam = toplam+ girilenParcala[j]*(j+1) * (j+1);
			}
			System.out.println("Aranacak Kelimenin Ascii Toplam� : "+ toplam);
			modArama = toplam %101;
			System.out.println("Aranacak Kelimenin Modu : "+modArama);
			modGecici = modArama;
			
			// E�er girdi�imiz kelimenin modu 100 dan b�y�kse ba�a d�nmeli.��nk� son indisimiz 99.
			if(modArama >=100)
			{
				modArama -=100;
			}
			//buldu�umuz mod indeksi bo�sa kelime yok demektir,if �al���yor ve fonksiyonlara yolluyor, 
			if(hash3[modArama]==0)
			{
				System.out.println("Arad���n�z "+ okunanDeger+ " text dosyam�zda bulunmamaktad�r.");
				harfEksilt(hash3,girilenParcala);
				harfDegistir(hash3,girilenParcala);
				break;				
			}
			//buldu�umuz mod indeksi bo� de�ilse burada quadratic mant�k ile 
			//hash dizisinde kelimenin ascii toplam� aran�yor,
			//bulunca ekrana yaz�yor.
			else
			{
				for(int k=0; k<100; k++)
				{
					if(modGecici >=100)
					{
						modGecici -=100;
					}
					if(hash3[modGecici]==toplam)
					{
						System.out.println("Arad���n�z "+okunanDeger+" text dosyam�zda bulunmaktad�r.");
						System.out.println("Bulunan isim hash dizimizin "+ modGecici+" indeksindedir.");
						say++;
						break;
					}
					else
					{
						//quadratic mant�k ile mod hesaplan�yor.
						modGecici = (modArama + (k+1) * (k+1)) % 101;
						if(modGecici >=100)
						{
							modGecici -=100;
						}
					}
				}
				if(say==0)
				{
					System.out.println("Arad���n�z "+ okunanDeger + " kelimesi text dosyam�zda bulunmamaktad�r.\n");
					harfEksilt(hash3,girilenParcala);
					harfDegistir(hash3,girilenParcala);
					
				}
				break;				
			
			}
			
			
		}
	}
	//Eger girilen kelime yoksa harfleri yer de�i�tirerek aramas� i�in harfDegistir() fonksiyonuna gelir.
	public void harfDegistir(int [] hash2, char [] girilenParca2)
	{
		char [] tut  = new char[1];
		char [] geciciTut = new char[girilenParca2.length];
		int araMod=0;
		
		int kontrol=0;
		int geciciMod1 = 0;
		String istenilenDeger = "";
		for(int k=0; k<girilenParca2.length; k++)
		{	
			int araToplam=0;
			//girilen isim gecici bir de�i�kende tutuyoruz.��nk� girilen isimi kaybetmememiz laz�m 
			//��nk� her seferde bu kelime �zerinden de�i�iklik yapaca��z.
			for(int j=0; j<girilenParca2.length; j++)
			{
				geciciTut[j] = girilenParca2[j];
			}
			
				//burada girilen isim biryandaki ile yer de�i�tiriyor fakat son eleman�n sa� taraf� olmad���
				// i�in diziden ta�ma olabilir bu ta�may� engellemek i�in bir kontrol if koyuyoruz.
				kontrol=k+1;
				if(kontrol <geciciTut.length)
				{
					
				//Burada for �al��t�k�a bir yandaki ile harfler takas yap�l�r.
				tut[0] = geciciTut[k];
				geciciTut[k] = geciciTut[k+1];
				geciciTut[k+1] = tut[0];
				
				for(int m=0; m<geciciTut.length; m++)
				{
					araToplam += geciciTut[m] * (m+1)* (m+1); //yer degistirilerek aranacak kelimenin ascii karsiligi hesaplaniyor.
					
				}
				araMod = araToplam %101;
				
				if(araMod>=100)
				{
					araMod -=100;
				}
				//E�er dizimizde yer de�i�tirerek benzerini bulduysak ekrana yazar.
				if(hash2[araMod] == araToplam)
				{
					System.out.print("Aranan kelime yer degistirilerek "+ araMod+" indeksinde su sekilde bulundu: ");
					for(int i=0; i<geciciTut.length;i++)
					{
						System.out.print(geciciTut[i]);
					}
					System.out.println("\n");
					
				}
				else
				{//quadratic mantik ile kelime araniyor.
					for(int n=0; n<hash2.length; n++)
					{
						geciciMod1 = (araMod + (n+1) * (n+1))%101;
						
						if(geciciMod1>=100)
						{
							geciciMod1 -=100;
						}
						
						if(hash2[geciciMod1] == araToplam)
						{
							System.out.print("Aranan kelime yer degistirilerek "+geciciMod1+" indeksinde su sekilde bulundu: ");
							for(int i=0; i<geciciTut.length;i++)
							{
								System.out.print(geciciTut[i]);
							}
							System.out.println("\n");
							
						}
					}
				}
				// Kullan�c�n�n girdi�i orjinal isimi geri y�kl�yoruz.
				//��nk� orjinal halinden harf de�i�tiriyoruz.
				/*if(true)
				{
					for(int l=0 ; l<girilenParca2.length; l++)
					{
						geciciTut[l] = girilenParca2[l];
					}
				}*/
				
			}
				
		}
		
	}
	
	
	//Eger girilen kelime yoksa harf eksilterek aramas� gerekiyor oy�zden harfEksilt() fonksiyonu �al���r
	public void harfEksilt(int [] hash1,char []girilenParca)
	{
			
			for(int i=0; i<girilenParca.length; i++)
			{	
				char [] sakla = new char[1];
				int eksiltToplam=0;
				int sayac=1;
				String bulunanDeger="";
				int eksilterekMod=0,arananGeciciMod=0;
				char parcaSakla []= new char [girilenParca.length];
				//girilen isimi kaybetmemek i�in birtane gecici char dizisine at�yoruz.
				for(int a=0; a<girilenParca.length; a++)
				{
					parcaSakla[a]=girilenParca[a];
				}
				sakla[0] =parcaSakla[i];
				parcaSakla[i] = '0';
				for(int j=0; j<girilenParca.length; j++)
				{
						if(parcaSakla[j] != '0')
						{
							eksiltToplam += parcaSakla[j] *(sayac * sayac) ; //harfini eksiltti�imiz ismin ascii kar��l���n� hesapl�yor.
							sayac++;
							bulunanDeger +=Character.toString(parcaSakla[j]); //harfi eksiltti�imiz ismi bulunanDeger adl� de�i�kende tutuyor.
							
						}
				}
				
				eksilterekMod = eksiltToplam %101;
				//E�er mod 100 ise 100. indis olmad��� i�in 0. indise d�nmesi gerekiyor.
				//Burada bunun kontrol� yap�l�yor.
				if(eksilterekMod>=100)
				{
					eksilterekMod -=100;
				}
				
				if(hash1[eksilterekMod] == eksiltToplam)
					{
						System.out.print("Aranan kelime silinerek "+eksilterekMod +" indeksinde su sekilde bulundu: "+ bulunanDeger+"\n");
							
					}
					else
					{
						for(int k=0; k<hash1.length; k++)
						{
							//quadratic mant�k ile hash dizisi i�inde kelimemizi bulmak i�in.
							arananGeciciMod = (eksilterekMod + (k+1)* (k+1)) %101;
							
							if(arananGeciciMod>=100)
							{
								arananGeciciMod -=100;
							}
							if(hash1[arananGeciciMod] == eksiltToplam)
							{
								System.out.print("Aranan kelime silinerek "+arananGeciciMod+" indeksinde su sekilde bulundu: "+bulunanDeger+"\n" );
							
							}
						}
					}
			}
			
		}

	public static void main(String[] args) throws IOException 
	{
		/*
		 * Program�n text dosyas�n� okuyup
		 * Kullan�c�dan 3 kere isim al�p aramas� i�in 3 kere nesne t�rettim.
		 */
		
		Main basla = new Main();
		Main basla1 = new Main();
		Main basla2 = new Main();
		
		basla.hashAtma();
		basla1.hashAtma();
		basla2.hashAtma();
	}
	
}
