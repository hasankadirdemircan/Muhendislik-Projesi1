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
		//Text dosyamýzý okumak için text ismimizi yazýyoruz.
		File file = new File("isim1.txt");
		//dosya okuyor
		BufferedReader reader = new BufferedReader(new FileReader(file));
		//satýr okunuyor
		String satir = reader.readLine();
		
		//Text dosyamýz sonuna kadar okunur ve
		// isim adlý string diziye atýlýr.
		while(satir != null)
		{
			isim[indis]=satir;
			satir = reader.readLine();
			indis++;
		}
		//Text dosyasýndan okuduðumuz ve string bir diziye attýðýmýz
		// isimleri char dizisine dönüþtürüp ascii karþýlýklarýný alýp 
		// hash dizisine quadratic mantýk ile koyar.
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
		
		// hash dizisini ekrana yazdýrýr..
		 
		for(int z=0; z<hash.length;z++)
		{
			System.out.println(z+". indis: "+hash[z]);
		}
		
		
		
		//Text dosyamýzdaki isimleri okuyup hash dizisine quadratic mantýk ile attýktan sonra 
		//kullanýcan isim alýp
		// aramak için kelimeGir() fonksiyonuna gönderiyoruz.
		kelimeGir(hash,isim);

	}
	
	public void kelimeGir(int[]hash3, String[] isim1)
	{
		/*
		 * klavyeden girilen isimi alýyoruz.
		 */
		Scanner scanner = new Scanner(System.in);
		System.out.print("Aranacak Kelimeyi Giriniz: ");
		String okunanDeger = scanner.nextLine();
		
		for(int i=0; i<isim1.length; i++)
		{
			int say=0;
			char girilenParcala[]= okunanDeger.toCharArray();
			int toplam=0,modArama=0,modGecici=0;
			
			//kullanýcýnýn girdiði kelimenin toplam ascii deðerini hesaplýyoruz.
			for(int j=0; j<girilenParcala.length; j++)
			{
				toplam = toplam+ girilenParcala[j]*(j+1) * (j+1);
			}
			System.out.println("Aranacak Kelimenin Ascii Toplamý : "+ toplam);
			modArama = toplam %101;
			System.out.println("Aranacak Kelimenin Modu : "+modArama);
			modGecici = modArama;
			
			// Eðer girdiðimiz kelimenin modu 100 dan büyükse baþa dönmeli.Çünkü son indisimiz 99.
			if(modArama >=100)
			{
				modArama -=100;
			}
			//bulduðumuz mod indeksi boþsa kelime yok demektir,if çalýþýyor ve fonksiyonlara yolluyor, 
			if(hash3[modArama]==0)
			{
				System.out.println("Aradýðýnýz "+ okunanDeger+ " text dosyamýzda bulunmamaktadýr.");
				harfEksilt(hash3,girilenParcala);
				harfDegistir(hash3,girilenParcala);
				break;				
			}
			//bulduðumuz mod indeksi boþ deðilse burada quadratic mantýk ile 
			//hash dizisinde kelimenin ascii toplamý aranýyor,
			//bulunca ekrana yazýyor.
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
						System.out.println("Aradýðýnýz "+okunanDeger+" text dosyamýzda bulunmaktadýr.");
						System.out.println("Bulunan isim hash dizimizin "+ modGecici+" indeksindedir.");
						say++;
						break;
					}
					else
					{
						//quadratic mantýk ile mod hesaplanýyor.
						modGecici = (modArama + (k+1) * (k+1)) % 101;
						if(modGecici >=100)
						{
							modGecici -=100;
						}
					}
				}
				if(say==0)
				{
					System.out.println("Aradýðýnýz "+ okunanDeger + " kelimesi text dosyamýzda bulunmamaktadýr.\n");
					harfEksilt(hash3,girilenParcala);
					harfDegistir(hash3,girilenParcala);
					
				}
				break;				
			
			}
			
			
		}
	}
	//Eger girilen kelime yoksa harfleri yer deðiþtirerek aramasý için harfDegistir() fonksiyonuna gelir.
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
			//girilen isim gecici bir deðiþkende tutuyoruz.Çünkü girilen isimi kaybetmememiz lazým 
			//Çünkü her seferde bu kelime üzerinden deðiþiklik yapacaðýz.
			for(int j=0; j<girilenParca2.length; j++)
			{
				geciciTut[j] = girilenParca2[j];
			}
			
				//burada girilen isim biryandaki ile yer deðiþtiriyor fakat son elemanýn sað tarafý olmadýðý
				// için diziden taþma olabilir bu taþmayý engellemek için bir kontrol if koyuyoruz.
				kontrol=k+1;
				if(kontrol <geciciTut.length)
				{
					
				//Burada for çalýþtýkça bir yandaki ile harfler takas yapýlýr.
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
				//Eðer dizimizde yer deðiþtirerek benzerini bulduysak ekrana yazar.
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
				// Kullanýcýnýn girdiði orjinal isimi geri yüklüyoruz.
				//Çünkü orjinal halinden harf deðiþtiriyoruz.
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
	
	
	//Eger girilen kelime yoksa harf eksilterek aramasý gerekiyor oyüzden harfEksilt() fonksiyonu çalýþýr
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
				//girilen isimi kaybetmemek için birtane gecici char dizisine atýyoruz.
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
							eksiltToplam += parcaSakla[j] *(sayac * sayac) ; //harfini eksilttiðimiz ismin ascii karþýlýðýný hesaplýyor.
							sayac++;
							bulunanDeger +=Character.toString(parcaSakla[j]); //harfi eksilttiðimiz ismi bulunanDeger adlý deðiþkende tutuyor.
							
						}
				}
				
				eksilterekMod = eksiltToplam %101;
				//Eðer mod 100 ise 100. indis olmadýðý için 0. indise dönmesi gerekiyor.
				//Burada bunun kontrolü yapýlýyor.
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
							//quadratic mantýk ile hash dizisi içinde kelimemizi bulmak için.
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
		 * Programýn text dosyasýný okuyup
		 * Kullanýcýdan 3 kere isim alýp aramasý için 3 kere nesne türettim.
		 */
		
		Main basla = new Main();
		Main basla1 = new Main();
		Main basla2 = new Main();
		
		basla.hashAtma();
		basla1.hashAtma();
		basla2.hashAtma();
	}
	
}
