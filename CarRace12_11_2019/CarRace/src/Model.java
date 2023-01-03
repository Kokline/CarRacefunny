import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Model {

	private static String FILENAME = "models.cars";

	char active;
	int id;
	int idBrand;
	String type;
	String name;

	private static String listTypes[] = { "Sedan", "Hatch", "SUV", "SPORT", "CAMIONETE", "COUPÉ" };

	public Model() {
		this.active = 'S';
		this.id = 0;
	}

	public Model(int idBrand, String type, String name) {
		this.active = 'S';
		this.id = 0;
		this.idBrand = idBrand;
		this.name = name;
		this.type = type;
	}

	public void setModel(int id, int idBrand, String type, String name) {
		this.active = 'S';
		this.id = id;
		this.idBrand = idBrand;
		this.name = name;
		this.type = type;
	}

	public void fillModelData() {
		System.out.println("\nCADASTRO DO MODELO =====================================");

		Brand.listAllBrand();
		Brand auxBrand;
		int idType;

		do {
			do {

				this.idBrand = LTPUtils.getIntPositivo("Informe o ID da MARCA: ");

				auxBrand = Brand.getBrandById(this.idBrand);
				if (auxBrand == null) {
					System.err.println("Informe um ID Válido!");
				} else {
					System.out.println("Marca Selecionada: " + auxBrand.name);
				}
			} while (auxBrand == null);

			do {

				showTypes();
				idType = LTPUtils.getIntPositivo("Informe o ID do TIPO: ");
				if (idType <= 0 || idType > listTypes.length) {
					System.err.println("Informe um ID Válido!");
				} else {
					this.type = listTypes[idType - 1];
					System.out.println("Tipo Selecionado: " + this.type);
				}
			} while (idType <= 0 || idType > listTypes.length);

			this.name = LTPUtils.getStringUpperCase("Digite o NOME do Modelo: ");

			this.getModel(this.name);

			if (this.id != -1) {
				this.showData();
				System.err.println("Cadastre outra!");
				System.out.println("============================");
			}
		} while (this.id != -1);
		this.id = autoIncrement();

		if (LTPUtils.recebeSouN("Deseja Salvar (S/N)? ") == 'S') {
			save();
		}
	}

	public void getModel(String name) {

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Model m = new Model();

			while (true) {
				m.active = file.readChar();
				m.id = file.readInt();
				m.idBrand = file.readInt();
				m.type = file.readUTF();
				m.name = file.readUTF();

				if (m.active == 'S' && m.name.equalsIgnoreCase(name)) {
					this.setModel(m.id, m.idBrand, m.type, m.name);
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
	
	public static Model getModelById(int id) {

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Model m = new Model();

			while (true) {
				m.active = file.readChar();
				m.id = file.readInt();
				m.idBrand = file.readInt();
				m.type = file.readUTF();
				m.name = file.readUTF();

				if (m.active == 'S' && m.id == id) {
					return m;
				}
			}

		} catch (EOFException e) {
			return null;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
		return null;
	}
	
	public static Model getModelByIdBrand(int id) {

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Model m = new Model();

			while (true) {
				m.active = file.readChar();
				m.id = file.readInt();
				m.idBrand = file.readInt();
				m.type = file.readUTF();
				m.name = file.readUTF();

				if (m.active == 'S' && m.idBrand == id) {
					return m;
				}
			}

		} catch (EOFException e) {
			return null;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
		return null;
	}

	public static void showTypes() {
		System.out.println("\n=================================");
		for (int i = 0; i < listTypes.length; i++) {
			System.out.println((i + 1) + " - " + listTypes[i]);
		}
		System.out.println("\n=================================");
	}

	public void showData() {
		System.out.println("MODELO =========");
		System.out.println("ID             : " + this.id);
		System.out.println("Marca          : " + Brand.getBrandById(this.idBrand).name);
		System.out.println("Tipo           : " + this.type);
		System.out.println("Nome           : " + this.name);
	}

	public void save() {
		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");

			file.seek(file.length());

			file.writeChar(this.active);
			file.writeInt(this.id);
			file.writeInt(this.idBrand);
			file.writeUTF(this.type);
			file.writeUTF(this.name);

			file.close();

			System.out.println("Arquivo: " + FILENAME + " salvo com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

	}

	public static void listAllModel() {
		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Model m = new Model();

			while (true) {
				m.active = file.readChar();
				m.id = file.readInt();
				m.idBrand = file.readInt();
				m.type = file.readUTF();
				m.name = file.readUTF();
				m.showData();
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
			Model m = new Model();

			while (true) {
				m.active = file.readChar();
				m.id = file.readInt();
				m.idBrand = file.readInt();
				m.type = file.readUTF();
				m.name = file.readUTF();

				id = m.id;
			}

		} catch (EOFException e) {
			return ++id;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

		return ++id;
	}

}
