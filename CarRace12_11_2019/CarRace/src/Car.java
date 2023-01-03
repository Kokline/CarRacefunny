import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Car {

	private static String FILENAME = "list.cars";

	char active;
	int idModel;
	String chassi; // Chave Primária
	String color;
	int hp;
	float capacity;
	String sponsor;
	
	
	private static String listColors[] = { "Azul", "Branco", "Cinza", "Dourado", "Verde", "Vermelho", "Prata", "Preto" };

	public Car() {
		this.active = 'S';
	}

	public Car(int idModel, String color, int hp, float capacity, String sponsor) {
		this.active = 'S';
		this.idModel = idModel;
		this.color = color;
		this.hp = hp;
		this.capacity = capacity;
		this.sponsor = sponsor;
	}

	public void setCar(int idModel, String color, int hp, float capacity, String sponsor) {
		this.active = 'S';
		this.idModel = idModel;
		this.color = color;
		this.hp = hp;
		this.capacity = capacity;
		this.sponsor = sponsor;
	}

	public void fillCarData() {
		System.out.println("\nCADASTRO DO CARRO =====================================");

		Model auxModel;
		int idColor;

		do {
			this.chassi = LTPUtils.getStringUpperCase("Digite o CHASSI do Carro: ");
			if(Car.getCarByChassi(this.chassi) != null) {
				System.err.println("CHASSI já cadastrado!");
			}
		} while (Car.getCarByChassi(this.chassi) != null);

		do {
			Model.listAllModel();
			this.idModel = LTPUtils.getIntPositivo("Informe o ID do Modelo: ");

			auxModel = Model.getModelById(this.idModel);
			if (auxModel == null) {
				System.err.println("Informe um ID Válido!");
			} else {
				System.out.print("Foi SELECIONADO O ");
				auxModel.showData();
			}
		} while (auxModel == null);

		
		
		this.color = LTPUtils.getItensInArray(listColors, "Cor");
				
		this.hp = LTPUtils.getIntPositivo("Digite a POTÊNCIA do Carro: ");
		this.capacity = LTPUtils.getFloatPositivo("Digite a CAPACIDADE do TANQUE do Carro: ");
		this.sponsor = LTPUtils.getStringUpperCase("Digite o PATROCINADOR: ");
	
		if (LTPUtils.recebeSouN("Deseja Salvar (S/N)? ") == 'S') {
			save();
		}
	}

	public void showData() {
		
		Model m = new Model();
		m = Model.getModelById(this.idModel);
		
		Brand b = new Brand();
		b = Brand.getBrandById(m.idBrand);
		
		System.out.println("CARRO =========");
		System.out.println("CHASSI       : " + this.chassi);
		System.out.println("Marca        : " + b.name);
		System.out.println("Modelo       : " + m.name);
		System.out.println("Cor          : " + this.color);
		System.out.println("Potência     : " + this.hp);
		System.out.println("Capacidade   : " + this.capacity);
		System.out.println("Patrocinador : " + this.sponsor);
	}

	public static Car getCarByChassi(String chassi) {

		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Car c = new Car();

			while (true) {
				c.active = file.readChar();
				c.idModel = file.readInt();
				c.chassi = file.readUTF();
				c.color = file.readUTF();
				c.hp = file.readInt();
				c.capacity = file.readFloat();
				c.sponsor = file.readUTF();

				if (c.active == 'S' && c.chassi.equals(chassi)) {
					return c;
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
			file.writeInt(this.idModel);
			file.writeUTF(this.chassi);
			file.writeUTF(this.color);
			file.writeInt(this.hp);
			file.writeFloat(this.capacity);
			file.writeUTF(this.sponsor);

			file.close();

			System.out.println("Arquivo: " + FILENAME + " salvo com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}

	}

	public static void listAllCars() {
		try {
			RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
			Car c = new Car();

			while (true) {
				c.active = file.readChar();
				c.idModel = file.readInt();
				c.chassi = file.readUTF();
				c.color = file.readUTF();
				c.hp = file.readInt();
				c.capacity = file.readFloat();
				c.sponsor = file.readUTF();

				c.showData();
			}

		} catch (EOFException e) {
			System.out.println("=====================================");
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo!");
		}
	}
	
	
}
