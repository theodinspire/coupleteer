package com.theodinspire.coupleteer.words;

/**
 * Created by corms on 5/25/17.
 */
public class Tags {
    private Tags() { }
    
    //  WordHoard tags for PTB
    private static final String whCC    = "c[cs](x|-acp)?";
    private static final String whCD    = "crd";
    private static final String whDET   = "(d[tx]?|pi2?x?)";
    private static final String whDETPOS = "(d|pi)g";
    private static final String whFW    = "fw-\\w\\w";
    private static final String whIN    = "([cp]-acp|pp-f)";
    private static final String whJJ    = "(ord|j(-[aju].*)?)";
    private static final String whJJR   = "[jd][cp](-\\w+)?";
    private static final String whJJS   = "[dj]s(-\\w+)?";
    private static final String whMD    = "vm\\w+";
    private static final String whNN    = "n1?(-[^v]+)?";
    private static final String whNNPOS = "ng1(-\\w+)?";
    private static final String whNNP   = "n(jp|p1)(-[nj])?";
    private static final String whNNPPOS = "nj?pg1(-[nj])?";
    private static final String whNNPS  = "nj?p2(-[nj])?";
    private static final String whNNPSPOS = "nj?pg2";
    private static final String whNNS   = "n2(-[^sv]\\w*)?";
    private static final String whNNSPOS = "ng2(-jn?)?";
    private static final String whPRP   = "p(n\\w+|x\\d\\d)";
    private static final String whPRPPOS = "pxg\\d+";
    private static final String whPRPS  = "po\\d+";
    private static final String whRB    = "(xx|a-acp|av(-(a?n|[cdx]|jn?(-u)?|vvn(-u)?))?)";
    private static final String whRBR   = "(av?-(d[cx]|jc)|avc-jn)";
    private static final String whRBS   = "av-[dj]?s";
    private static final String whRP    = "pc-acp";
    private static final String whSYM   = "(zz|(n2-)?sy)";
    private static final String whUH    = "uh(-\\w+)?";
    private static final String whVB    = "v[bdhv][bip](-u)?";
    private static final String whVBD   = "v[bdhv]d2?(-u)?[rs]?";
    private static final String whVBG   = "(\\w[2v]?-)?v[bdhv]g(-u)?";
    private static final String whVBN   = "([jn]-)?v[bdhv]n(-u)?";
    private static final String whVBP   = "v[bdhv]2?[rsm]?";
    private static final String whVBZ   = "v[bdhv]z(-\\w+)?";
    private static final String whWDT   = "(cst|r[go]?-crq)";
    private static final String whWP    = "(n|qo?)-crq";
    private static final String whWPS   = "qg-crq";
    private static final String whWRB   = "c-crq";
    
    //  Penn Tree Bank, PTB, tags
    public static final String CC      = "CC";
    public static final String CD      = "CD";
    public static final String DET     = "DET";
    public static final String DETPOS  = "DET+POS";
    public static final String FW      = "FW";
    public static final String IN      = "IN";
    public static final String JJ      = "JJ";
    public static final String JJR     = "JJR";
    public static final String JJS     = "JJS";
    public static final String MD      = "MD";
    public static final String NN      = "NN";
    public static final String NNPOS   = "NN+POS";
    public static final String NNP     = "NNP";
    public static final String NNPPOS  = "NNP+POS";
    public static final String NNPS    = "NNPS";
    public static final String NNPSPOS = "NNPS+POS";
    public static final String NNS     = "NNS";
    public static final String NNSPOS  = "NNS+POS";
    public static final String PRP     = "PRP";
    public static final String PRPPOS  = "PRP+POS";
    public static final String PRPS    = "PRP$";
    public static final String RB      = "RB";
    public static final String RBR     = "RBR";
    public static final String RBS     = "RBS";
    public static final String RP      = "RP";
    public static final String SYM     = "SYM";
    public static final String TO      = "TO";
    public static final String UH      = "UH";
    public static final String VB      = "VB";
    public static final String VBD     = "VBD";
    public static final String VBG     = "VBG";
    public static final String VBN     = "VBN";
    public static final String VBP     = "VBP";
    public static final String VBZ     = "VBZ";
    public static final String WDT     = "WDT";
    public static final String WP      = "WP";
    public static final String WPS     = "WP$";
    public static final String WRB     = "WRB";
    
    static private final String BOL = "BOL";
    static private final String EOL = "EOL";
    
    public static String getStartOfLine() { return BOL; }
    public static String getEndOfLine() { return EOL; }
    
    public static String[] convertWoardHoardTag(String tag) {
        if (tag.matches("[\\w\\-]+\\|[\\w\\-]+")) {
            String[] tags = tag.split("\\|");
            String[] ary = new String[tags.length];
            
            for (int i = 0; i < tags.length; ++i) ary[i] = wordHoardToPTB(tags[i]);
            
            return ary;
        } else {
            String newTag = wordHoardToPTB(tag);
            if (newTag.matches("\\w+\\+POS")) {
                String[] ary = newTag.split("\\+");
                return ary;
            } else {
                String[] ary = { newTag };
                return ary;
            }
        }
    }
    
    private static String wordHoardToPTB(String tag) {
        if (tag.matches(whCC))      return CC;
        if (tag.matches(whCD))      return CD;
        if (tag.matches(whDET))     return DET;
        if (tag.matches(whDETPOS))  return DETPOS;
        if (tag.matches(whFW))      return FW;
        if (tag.matches(whIN))      return IN;
        if (tag.matches(whJJ))      return JJ;
        if (tag.matches(whJJR))     return JJR;
        if (tag.matches(whJJS))     return JJS;
        if (tag.matches(whMD))      return MD;
        if (tag.matches(whNN))      return NN;
        if (tag.matches(whNNP))     return NNP;
        if (tag.matches(whNNPOS))   return NNPOS;
        if (tag.matches(whNNPPOS))  return NNPPOS;
        if (tag.matches(whNNS))     return NNS;
        if (tag.matches(whNNSPOS))  return NNSPOS;
        if (tag.matches(whNNPS))    return NNPS;
        if (tag.matches(whNNPSPOS)) return NNPSPOS;
        if (tag.matches(whPRP))     return PRP;
        if (tag.matches(whPRPPOS))  return PRPPOS;
        if (tag.matches(whPRPS))    return PRPS;
        if (tag.matches(whRB))      return RB;
        if (tag.matches(whRBR))     return RBR;
        if (tag.matches(whRBS))     return RBS;
        if (tag.matches(whRP))      return RP;
        if (tag.matches(whSYM))     return SYM;
        if (tag.matches(whUH))      return UH;
        if (tag.matches(whVB))      return VB;
        if (tag.matches(whVBD))     return VBD;
        if (tag.matches(whVBG))     return VBG;
        if (tag.matches(whVBN))     return VBN;
        if (tag.matches(whVBP))     return VBP;
        if (tag.matches(whVBZ))     return VBZ;
        if (tag.matches(whWDT))     return WDT;
        if (tag.matches(whWP))      return WP;
        if (tag.matches(whWPS))     return WPS;
        if (tag.matches(whWRB))     return WRB;
        else                        return "";
    }
}
