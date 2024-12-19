import DataBase.SqlFunctions;

import java.util.ArrayList;

public class ClientDAO {
    public Client getClientById(int id) {
        SqlFunctions sql = new SqlFunctions();

        ArrayList<String> items;

        items = sql.selectById("CLIENTE", id);

        id = Integer.parseInt(items.get(0));
        String name = items.get(1);
        double balance = Double.parseDouble(items.get(2));

        return new Client(id, name, balance);
    }
}
