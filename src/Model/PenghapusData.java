package Model;

import java.sql.SQLException;

public interface PenghapusData {
    boolean hapusData(int id) throws SQLException;
}