import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Brand {

	private static String FILENAME = "brand.cars";

	char active;
	int id;
	String name;
	String country;

	public Brand() {
		this.active = 'S';
		this.id = 0;
	}

	public Brand(String name, String country) {
		this.active = 'S';
		this.id = 0;
		this.name = name;
		this.country = country;
	}

	public void setBrand(int id, String name, String country) {
		this.active = 'S';
		this.id = id;
		this.name = name;
		this.country = country;
	}

	public void fillBrandData() {
		System.out.println("\nCADASTRO DO MARCAS =====================================");

		do {
			this.name = LTPUtils.getStringUpperCase("Digite o NOME da marca: ");
			this.getBrand(this.name);
			// Se encontrou a marca já cadastrada, mostra
			if (this.id != -1) {
				this.showData();
				System.err.println("Cadastre outra!");
			}
		} while (this.id != -1);
		this.id = autoIncrement();
		this.country = LTPUtils.getItensInArray(Country.list, "PAÍS de ORIGEM");
		if (LTPUtils.recebeSouN("Deseja Salvar (S/N)? ") == 'S') {
			save();
		}
	}

	public void showData() {
		System.out.println("MARCA =========");
		System.out.println("ID             : " + this.id);
		System.out.println("Nome           : " + this.name);
		System.out.println("País de Origem : " + this.country);
	}

	public void getBrand(String name) {

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Brand b = new Brand();

			while (true) {
				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();
				if (b.active == 'S' && b.name.equalsIgnoreCase(name)) {
					this.setBrand(b.id, b.name, b.country);
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

	public static Brand getBrandById(int id) {

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Brand b = new Brand();

			while (true) {
				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();
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

	public static long getPointerBrandById(int id) {

		long pointer;

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Brand b = new Brand();

			while (true) {
				pointer = file.getFilePointer();

				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();
				if (b.active == 'S' && b.id == id) {
					return pointer;
				}
			}

		} catch (EOFException e) {
			return -1;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
		return -1;
	}

	public void save() {
		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");

			file.seek(file.length());

			file.writeChar(this.active);
			file.writeInt(this.id);
			file.writeUTF(this.name);
			file.writeUTF(this.country);

			file.close();

			System.out.println("Arquivo: " + FILENAME + " salvo com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

	}

	public static void deleteById(int id) {

		try {

			long pointer = getPointerBrandById(id);

			if (pointer != -1) {
				if (Model.getModelByIdBrand(id) == null) {
					RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");

					file.seek(pointer);

					file.writeChar('N');

					file.close();

					System.out.println("ID (" + id + "): excluído com sucesso!");
				} else {
					System.err.println("Essa marca contém modelos cadastrados!\nNão é possível excluir!");
				}

			} else {
				System.err.println("ID não encontrado!");
			}

		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
	}

	public static void updateById(int id) {

		// Recupera o Brand Atual
		Brand atual = Brand.getBrandById(id);

		if (atual != null) {

			if (Model.getModelByIdBrand(id) == null) {

				// Receber o novo
				Brand novo = new Brand();

				// Trocar nome
				if (LTPUtils.recebeSouN("NOME: " + atual.name + "\nTrocar (S/N)? ") == 'S') {
					novo.name = LTPUtils.getStringUpperCase("Novo NOME: ");
				} else {
					novo.name = atual.name;
				}

				// Trocar País
				if (LTPUtils.recebeSouN("PAÍS: " + atual.country + "\nTrocar (S/N)? ") == 'S') {
					novo.country = LTPUtils.getStringUpperCase("Novo PAÍS: ");
				} else {
					novo.country = atual.country;
				}

				novo.id = atual.id;

				// Se existe, irá desativar o atual
				deleteById(id);

				novo.save();

				System.out.println("ID (" + id + "): alterado com sucesso!");
			} else {
				System.err.println("Essa marca contém modelos cadastrados!\nNão é possível alterar!");
			}

		} else {
			System.err.println("ID não encontrado!");
		}
	}

	public static void listAllBrand() {
		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Brand b = new Brand();

			while (true) {
				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();
				if (b.active == 'S') {
					b.showData();
				}
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
			Brand b = new Brand();

			while (true) {
				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();

				id = b.id;
			}

		} catch (EOFException e) {
			return ++id;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

		return ++id;
	}

	public static int totalBrands() {
		int cont = 0;

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Brand b = new Brand();

			while (true) {
				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();

				if (b.active == 'S') {
					cont++;
				}
			}

		} catch (EOFException e) {
			return cont;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

		return cont;
	}

	public static void reportTotalRegisterBrand() {
		int totalBrand = totalBrands();
		Brand listBrand[] = new Brand[totalBrand];
		int contRegisterBrand[] = new int[totalBrand];
		int i = 0;

		// Preencher o vetor de Marcas
		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");

			while (true) {
				Brand b = new Brand();
				b.active = file.readChar();
				b.id = file.readInt();
				b.name = file.readUTF();
				b.country = file.readUTF();
				if (b.active == 'S') {
					listBrand[i++] = b;
				}

			}

		} catch (EOFException e) {

		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

		// Carregar os carros e contar número de marcas
		try {
			RandomAccessFile file = new RandomAccessFile("list.cars", "rw");
			Car c = new Car();

			while (true) {
				c.active = file.readChar();
				c.idModel = file.readInt();
				c.chassi = file.readUTF();
				c.color = file.readUTF();
				c.hp = file.readInt();
				c.capacity = file.readFloat();
				c.sponsor = file.readUTF();

				if (c.active == 'S') {

					Model mCar = Model.getModelById(c.idModel);

					for (i = 0; i < listBrand.length; i++) {
						if (listBrand[i].id == mCar.idBrand) {
							contRegisterBrand[i]++;
							break;
						}
					}
				}
			}

		} catch (EOFException e) {
			System.out.println("=================================");
			// Mostrar todas as marcas e quantidades
			for (int j = 0; j < contRegisterBrand.length; j++) {
				System.out.println("MARCA: " + listBrand[j].name);
				System.out.println("TOTAL: " + contRegisterBrand[j]);

			}
			System.out.println("=================================");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
	}
}
