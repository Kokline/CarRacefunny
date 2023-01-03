import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Sponsor {
	private static String FILENAME = "sponsor.cars";

	char active;
	int id;
	String name;
	String country;
	float investiment;

	public Sponsor() {
		this.active = 'S';
		this.id = 0;
	}

	public Sponsor(String name, String country, float investiment) {
		this.active = 'S';
		this.id = 0;
		this.name = name;
		this.country = country;
		this.investiment = investiment;
	}

	public void setSponsor(int id, String name, String country, float investiment) {
		this.active = 'S';
		this.id = id;
		this.name = name;
		this.country = country;
		this.investiment = investiment;
	}

	public void fillSponsorData() {
		System.out.println("\nCADASTRO DO PATROCINADORES =====================================");

		do {
			this.name = LTPUtils.getStringUpperCase("Digite o NOME do Patrocinador: ");
			this.getSponsor(this.name);
			// Se encontrou a marca já cadastrada, mostra
			if (this.id != -1) {
				this.showData();
				System.err.println("Cadastre outra!");
			}
		} while (this.id != -1);
		this.id = autoIncrement();
		this.country = LTPUtils.getItensInArray(Country.list, "PAÍS de ORIGEM");
		this.investiment = LTPUtils.getFloatPositivo("Informe o valor padrão de INVESTIMENTO: ");
	
		if (LTPUtils.recebeSouN("Deseja Salvar (S/N)? ") == 'S') {
			save();
		}
	}

	public void showData() {
		System.out.println("PATROCINADOR ======================");
		System.out.println("ID                    : " + this.id);
		System.out.println("Nome                  : " + this.name);
		System.out.println("País de Origem        : " + this.country);
		System.out.println("Valor do Investimento : " + LTPUtils.formatacaoReal(this.investiment));
	}

	public void getSponsor(String name) {

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Sponsor b = new Sponsor();

			while (true) {
				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();
				b.investiment = file.readFloat();
				if (b.active == 'S' && b.name.equalsIgnoreCase(name)) {
					this.setSponsor(b.id, b.name, b.country, b.investiment);
					break;
				}
			}

			file.close();

		} catch (EOFException e) {
			this.id = -1;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
	}

	public static Sponsor getSponsorById(int id) {

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Sponsor b = new Sponsor();

			while (true) {
				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();
				b.investiment = file.readFloat();
				if (b.active == 'S' && b.id == id) {
					return b;
				}
			}

		} catch (EOFException e) {
			return null;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
		return null;
	}

	public void save() {
		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");

			file.seek(file.length());

			file.writeChar(this.active);
			file.writeInt(this.id);
			file.writeUTF(this.name);
			file.writeUTF(this.country);
			file.writeFloat(this.investiment);

			file.close();

			System.out.println("Arquivo: " + FILENAME + " salvo com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

	}

	public static void listAllSponsor() {
		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Sponsor b = new Sponsor();

			while (true) {
				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();
				b.investiment = file.readFloat();
				b.showData();
			}

		} catch (EOFException e) {
			System.out.println("=====================================");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
	}

	public static int autoIncrement() {
		int id = 0;

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Sponsor b = new Sponsor();

			while (true) {
				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();
				b.investiment = file.readFloat();
				
				id = b.id;
			}

		} catch (EOFException e) {
			return ++id;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

		return ++id;
	}
}
