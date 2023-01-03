
public class Manager {
	
	private static String menuHome[] = {"Marca","Modelo","Patrocinador","Carro"};
	private static String menuCrud[] = {"Cadastrar","Alterar","Excluir","Listar","Pesquisar Pelo Nome","Pesquisar Pelo ID"}; 
	private static String menuCrudBrand[] = {"Cadastrar","Alterar","Excluir","Listar","Pesquisar Pelo Nome","Pesquisar Pelo ID","Total de Carros por MARCA"}; 

	
	public static void main(String[] args) {
		menuCategory();
	}

	public static void menuCategory() {
		
		do {
			
			switch (LTPUtils.menuOptions("SISTEMA DE GESTÃO CAR RACE", menuHome)) {
			case 1:
				menuMarca();
				break;
			case 2:
				menuModelo();
				break;
			case 3:

				break;
			case 4:
				menuCar();
				break;
			case 0:
				return;
			}
		} while (true);

	}
	
	public static void menuMarca() {
		Brand b = new Brand();

		do {
			
			switch (LTPUtils.menuOptions("GESTÃO DE MARCAS", menuCrudBrand)) {
			case 1:
				b.fillBrandData();
				break;
			case 2:
				Brand.listAllBrand();
				Brand.updateById(LTPUtils.getIntPositivo("Informe o ID da MARCA para ser Alterado: "));
				break;
			case 3:
				Brand.listAllBrand();
				Brand.deleteById(LTPUtils.getIntPositivo("Informe o ID da MARCA para ser Excluído: "));
				break;
			case 4:
				Brand.listAllBrand();
				break;
			case 5:
				
				break;
			case 6:
				
				break;
			case 7:
				Brand.reportTotalRegisterBrand();
				break;
			case 0:
				return;

			}
		} while (true);

	}

	public static void menuModelo() {
		Model b = new Model();

		do {
			
			switch (LTPUtils.menuOptions("GESTÃO DE MODELOS", menuCrud)) {
			case 1:
				b.fillModelData();
				break;
			case 2:

				break;
			case 3:
				Model.listAllModel();
				//Model.deleteById(LTPUtils.getIntPositivo("Informe o ID do MODELO para ser Excluído: "));
				break;
			case 4:
				Model.listAllModel();
				break;
			case 5:
				
				break;
			case 6:
				
				break;
			case 0:
				return;

			}
		} while (true);

	}
	
	public static void menuCar() {
		Car c = new Car();

		do {
			
			switch (LTPUtils.menuOptions("GESTÃO DE CARROS", menuCrud)) {
			case 1:
				c.fillCarData();
				break;
			case 2:

				break;
			case 3:
				Car.listAllCars();
				//Model.deleteById(LTPUtils.getIntPositivo("Informe o ID do MODELO para ser Excluído: "));
				break;
			case 4:
				Car.listAllCars();
				break;
			case 5:
				
				break;
			case 6:
				
				break;
			case 0:
				return;

			}
		} while (true);

	}


}
