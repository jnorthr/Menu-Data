def dir = new File('.')
def hasMenu(fn)
{
    def flag =false;
    def fi = new File(fn);
    def tx = fi.text;
    tx.eachLine{ln->
        def ix = ln.indexOf(":=");
        if (ix > -1)
        {
            def cmd = ln.substring(ix+2).trim().toLowerCase();
            if (cmd.equals("*menutitle"))
            {
                flag = true;
                println "File $fn is named "+ln.substring(0,ix);
            } // end of if
        }
    } // end of
    
    return flag; 
} // end of has Menu

int ct = 0;
dir.eachFileMatch(~/.*.txt$/) {fn->
    def hm = hasMenu(fn.name);
    if (!hm) {println "found:"+fn.name+" without menu"; } 
    else 
    {
        ct += 1;
        MenuDecoder md = new MenuDecoder()
        md.outfilename = md.getNewName(fn.name);
        md.parse(fn.name);
        if (md.ok)
        {
            md.render();
        } // end of if        
    } // end of else

} // end of each

println "-----------\nfound $ct menus"