/*
 *  Copyright (C) 2010 rickcharon
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package petrasys.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author rickcharon
 */
public class postgresqlTest {

  public static void distinctSymbolInfos() {
    PreparedStatement pstmt = null;
    try {
//      pstmt = DBops.setuptradesConnection().prepareStatement("SELECT * from futuresContractDetails");
//      ResultSet res = pstmt.executeQuery();
      ResultSet res = DBops.distinctSymbolInfos().executeQuery();
      while (res.next()) {
        System.out.println(res.getString("symbol") + "\t" + res.getString("fullName"));
      }
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
    }

  }

  public static void datedRangeBySymbol() {
    CallableStatement ret = null;
    try {
      ret = DBops.setuptradesConnection().
              prepareCall("select * from datedRangeBySymbol('GBP', '2009-12-08 00:00:00', '2009-12-08 00:03:00');",
              ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY);
      ResultSet res = ret.executeQuery();
      while (res.next()) {
        System.out.println(res.getTimestamp("datetime") + "\t" + res.getDouble("close"));
      }

    } catch (SQLException ex) {
      MsgBox.err2(ex);
    }
  }

  public static void main(String[] args) {
    postgresqlTest.datedRangeBySymbol();
  }
}
