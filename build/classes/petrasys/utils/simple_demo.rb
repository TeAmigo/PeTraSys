######################################################################
## File path:     /share/JavaDev/PeTraSys/src/petrasys/utils/simple_demo.rb
## Version:       
## Description:   From http://jnb.ociweb.com/jnb/jnbJan2007.html
## Author:        Rick Charon <rickcharon@gmail.com>
## Created at:    Sun Dec 19 13:17:34 2010
## Modified at:   Sun Dec 19 13:17:50 2010
######################################################################

require 'java'

def show(message)
  # Passing in nil for dialog parent so it will show by itself
  javax.swing.JOptionPane.showMessageDialog(nil, message)
end

show($message)

