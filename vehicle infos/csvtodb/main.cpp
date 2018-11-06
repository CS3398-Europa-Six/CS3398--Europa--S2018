/***** Chase LP
 * Program to convert csv to specific format for group project
 */
/***Designed for incoming csv file, epa - 16tstcar.csv, the top line is column header, second is the first line***/
//"Model Year","Vehicle Manufacturer Name","Veh Mfr Code","Represented Test Veh Make","Represented Test Veh Model","Test Vehicle ID","Test Veh Configuration #","Test Veh Displacement (L)","Actual Tested Testgroup","Vehicle Type","Rated Horsepower","# of Cylinders and Rotors","Engine Code","Tested Transmission Type Code","Tested Transmission Type","# of Gears","Transmission Lockup?","Drive System Code","Drive System Description","Transmission Overdrive Code","Transmission Overdrive Desc","Equivalent Test Weight (lbs.)","Axle Ratio","N/V Ratio","Shift Indicator Light Use Cd","Shift Indicator Light Use Desc","Test Number","Test Originator","Analytically Derived FE?","ADFE Test Number","ADFE Total Road Load HP","ADFE Equiv. Test Weight (lbs.)","ADFE N/V Ratio","Test Procedure Cd","Test Procedure Description","Test Fuel Type Cd","Test Fuel Type Description","Test Category","THC (g/mi)","CO (g/mi)","CO2 (g/mi)","NOx (g/mi)","PM (g/mi)","CH4 (g/mi)","N2O (g/mi)","RND_ADJ_FE","FE_UNIT","FE Bag 1","FE Bag 2","FE Bag 3","FE Bag 4","DT-Inertia Work Ratio Rating","DT-Absolute Speed Change Ratg","DT-Energy Economy Rating","Target Coef A (lbf)","Target Coef B (lbf/mph)","Target Coef C (lbf/mph**2)","Set Coef A (lbf)","Set Coef B (lbf/mph)","Set Coef C (lbf/mph**2)","Aftertreatment Device Cd","Aftertreatment Device Desc","Police - Emergency Vehicle?","Averaging Group ID","Averaging Weighting Factor","Averaging Method Cd","Averging Method Desc"
//2016,"aston martin","ASX","Aston Martin","DB9","143TT1042",0,5.9,"DASXV05.9VH1","Car",510,12,"AM11/","SA","Semi-Automatic",6,"Y","R","2-Wheel Drive, Rear","2","Top gear ration < 1",4500,3.46,31,"1","Not eqipped","DASX10022045","MFR","No","",,,,"21","Federal fuel 2-day exhaust (w/can load)","61","Tier 2 Cert Gasoline","FTP",0.0251,0.12,550.27,0.02,,0.0033,0.0024,16.1,"MPG",15.803,15.157,18.626,,,,,43.17,0.7502,0.0163,8.35,0.299,0.0192,"TWC","Three-way catalyst","N","",,"N","No averaging"
//
/***Output file, tablebuilder.sql, will have the text in quotes, one line for each statement***/
//the statements are formatted to work with Java using JDBC to create an SQLite database file
//öutput format:
//stmt.executeUpdate("create table sample(id, name)");
//stmt.executeUpdate("insert into sample values(1, \"leo\")");
//stmt.executeUpdate("insert into sample values(2, \"yui\")");

#include <iostream>
#include <fstream>
#include <string>

const char * tablename = "epa2016";
const char * infilename = "epa - 16tstcar.csv";
const char * outfilename = "epa16tstcar.sql";
/***column exclusion, if desired, must set BOTH constants below***/
const short excludedcols [] = { }; //cannot exclude the first column, first column = 0, second = 1, ...
//excludedcols [] = { 2, 4 }; //this would exclude the third and fifth columns
const short excludedcolscount = 0;//size of excludedcols[] (total number of excluded columns)

bool isexcluded(short checkme) {
    if (excludedcolscount < 1) return false;
    for (int i=0;i<excludedcolscount;++i) {
        if (excludedcols[i] == checkme) return true;
    }
    return false;
}

int main() {
    std::string buffa;
    std::string outbuffa;
    std::ifstream infile;
    short currentelement;
    infile.open(infilename, std::ios::in);
    if (!infile.is_open()) {
        std::cout << "error opening " << infilename << " !!!\n";
        return 0;
    }
    std::ofstream outfile;
    outfile.open(outfilename, std::ios::out | std::ios::trunc);
    if (!outfile.is_open()) {
        std::cout << "error opening " << outfilename << " !!!\n";
        infile.close();
        return 0;
    }
    if (!std::getline(infile,buffa)) {
        infile.close();
        outfile.close();
        std::cout << "Error reading first line of input file!\n";
        return 0;
    }
//stmt.executeUpdate("create table sample(id, name)");
//stmt.executeUpdate("insert into sample values(1, \"leo\")");
//stmt.executeUpdate("insert into sample values(2, \"yui\")");
    outbuffa = "create table ";
    outbuffa += tablename;
    outbuffa += "(";
    currentelement = 0;
    for (int i= 1;i<buffa.length()-1;++i) {
        if (buffa[i] == '\"' || buffa[i] == ' ' ||
            (!isalnum(buffa[i]) && buffa[i] != ',') ) continue;
        outbuffa += buffa[i];
        if (buffa[i] == ',') {
            outbuffa += " ";
            ++currentelement;
            if (isexcluded(currentelement)) {
                ++currentelement;
                for (++i;i<buffa.length();++i) {
                    if (buffa[i] == ',') break;
                }
            }
        }
    }
    outbuffa += ")\n";
    outfile << outbuffa;
    while (std::getline(infile,buffa)) {
        currentelement = 0;
        outbuffa = "insert into ";
        outbuffa += tablename;
        outbuffa += " values(";
        for (int i=0;i<buffa.length();++i) {
            if (buffa[i] == '\"')  //double quotes need a single quote
                outbuffa += "\'";
            else {
                if (buffa[i] != ',')
                    outbuffa += buffa[i];
                else {
                    //make sure we don't have a complete empty field
                    if (i > 1 && buffa[i-1] == ',')
                        outbuffa += "\'\'";
                        outbuffa += ", ";
                    }
                    ++currentelement;
                if (isexcluded(currentelement)) {
                    ++currentelement;
                    for (++i;i<buffa.length();++i) {
                        if (buffa[i] == ',') break;
                    }
                }
            }
        }
        outbuffa += ")\n";
        outfile << outbuffa;
    }
    infile.close();
    outfile.close();
    return 0;
}