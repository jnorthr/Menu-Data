public class MenuDecoder{

def map =[:]
def menuname = "";
def filename = "unknown file";
String outfilename = "unknown file";
def ok = false; // false if not a menu file

// logic
def say(tx){println tx;}

public getNewName(String fn)
{
    int ix = fn.lastIndexOf(".txt");
    def ofn = fn.substring(0,ix)+".adoc"
    say "From file named |${fn}| we have:"+ofn+";";
    return ofn
} // end of get

public parse(String fn)
{
  def lines = new File(fn).text;
  
  lines.eachLine{ln ->
    //print ln+" = ";
    int ix = ln.indexOf(":=");
    if (ix > -1)
    {
        def cmd = ln.substring(ix+2).trim();
        def title = ln.substring(0,ix).trim();

        def lc = cmd.toLowerCase();

        def flag = (lc.startsWith("*menutitle")) ? true : false; 
        if (lc.startsWith("open ")) { cmd = cmd.substring(5);}

        if (flag)
        {
            say "= "+title
            menuname = title;
            ok = true;
        }
        else
        {
            say cmd + "[${title}]"
            map[title] = cmd;
        } // end of else
        
    } // end of if
    else
    {
        if (ln.trim().size() > 0)
        { 
            say "====> not a menu:"+fn+" line:|"+ln+"|"
        } // end of if
    } // end of else
  } // end of each
  
} // end of parse

public render()
{
    say "\n---------------------------"
    Map sorted = map.sort { a, b -> a.key <=> b.key }

    say "===========\noutput File name:"+outfilename
    def of = new File(outfilename);
    of.write "== "+menuname+'\n\n';

    // output the results, menu name first
    say "== "+menuname+" ( from ${filename} )\n" 
    sorted.each{k,v-> 
        def txt = v+"["+k+"]\n"; 
        say txt;
        of << txt+'\n';
    } // end of ech
    
} // end of render

    public static void main(String[] args)
    {
        MenuDecoder md = new MenuDecoder()
        String fn = "/Users/eve/Google Drive/Menu-Data/stylesheets.txt";
        md.outfilename = md.getNewName(fn);
        md.parse(fn);
        if (md.ok)
        {
            md.render();
        } // end of if
        md.say "--- the end ---"
    } // end of main
} // end of class