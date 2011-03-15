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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.*;

/**
 *
 * @author rickcharon
 */
public class jRubyTest {

  public static void main1(String[] args) throws Exception {
    ScriptEngineManager factory = new ScriptEngineManager();

    // Create a JRuby engine.
    ScriptEngine engine = factory.getEngineByName("jruby");

    // Evaluate JRuby code from string.
    try {
      engine.eval("puts('Hello')");
    } catch (ScriptException exception) {
      exception.printStackTrace();
    }
  }

  public static void main(String[] args) throws ScriptException {
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("ruby");
    engine.eval("puts('Hello from jRuby')");
    //String jRubyFileIn = System.getProperty("user.dir") + "/simple_demo.rb";
    String jRubyFileIn = "/share/JavaDev/PeTraSys/src/petrasys/utils/simple_demo.rb";
    engine.put("message", "Hello, world!");
    javax.swing.JOptionPane.showMessageDialog(null, "hi");
    try {
      engine.eval(new BufferedReader(new FileReader(jRubyFileIn)));
    } catch (FileNotFoundException ex) {
      System.err.println(ex.getMessage());
    }
    int i = 3;
  }
}
