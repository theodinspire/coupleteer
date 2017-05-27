package com.theodinspire;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
    private static final String whFW    = "fw\\-\\w\\w";
    private static final String whIN    = "([cp]\\-acp|pp\\-f)";
    private static final String whJJ    = "(ord|j(\\-[aju].*)?)";
    private static final String whJJR   = "[jd][cp](\\-\\w+)?";
    private static final String whJJS   = "[dj]s(\\-\\w+)?";
    private static final String whMD    = "vm\\w+";
    private static final String whNN    = "n1?(\\-[^v]+)?";
    private static final String whNNPOS = "ng1(\\-\\w+)?";
    private static final String whNNP   = "n(jp|p1)(\\-[nj])?";
    private static final String whNNPPOS = "nj?pg1(\\-[nj])?";
    private static final String whNNPS  = "nj?p2(\\-[nj])?";
    private static final String whNNPSPOS = "nj?pg2";
    private static final String whNNS   = "n2(\\-[^sv]\\w*)?";
    private static final String whNNSPOS = "ng2(\\-jn?)?";
    private static final String whPRP   = "p(n\\w+|x\\d\\d)";
    private static final String whPRPPOS = "pxg\\d+";
    private static final String whPRPS  = "po\\d+";
    private static final String whRB    = "(xx|a-acp|av(\\-(a?n|[cdx]|jn?(\\-u)?|vvn(\\-u)?))?)";
    private static final String whRBR   = "(av?-(d[cx]|jc)|avc-jn)";
    private static final String whRBS   = "av\\-[dj]?s";
    private static final String whRP    = "pc-acp";
    private static final String whSYM   = "(zz|(n2\\-)?sy)";
    private static final String whUH    = "uh(\\-\\w+)?";
    private static final String whVB    = "v[bdhv][bip](\\-u)?";
    private static final String whVBD   = "v[bdhv]d2?(\\-u)?[rs]?";
    private static final String whVBG   = "(\\w[2v]?\\-)?v[bdhv]g(\\-u)?";
    private static final String whVBN   = "([jn]\\-)?v[bdhv]n(\\-u)?";
    private static final String whVBP   = "v[bdhv]2?[rsm]?";
    private static final String whVBZ   = "v[bdhv]z(\\-\\w+)?";
    private static final String whWDT   = "(cst|r[go]?-crq)";
    private static final String whWP    = "(n|qo?)-crq";
    private static final String whWPS   = "qg-crq";
    private static final String whWRB   = "c-crq";
    
    //  Penn Tree Bank, PTB, tags
    static final String CC      = "CC";
    static final String CD      = "CD";
    static final String DET     = "DET";
    static final String DETPOS  = "DET+POS";
    static final String FW      = "FW";
    static final String IN      = "IN";
    static final String JJ      = "JJ";
    static final String JJR     = "JJR";
    static final String JJS     = "JJS";
    static final String MD      = "MD";
    static final String NN      = "NN";
    static final String NNPOS   = "NN+POS";
    static final String NNP     = "NNP";
    static final String NNPPOS  = "NNP+POS";
    static final String NNPS    = "NNPS";
    static final String NNPSPOS = "NNPS+POS";
    static final String NNS     = "NNS";
    static final String NNSPOS  = "NNS+POS";
    static final String PRP     = "PRP";
    static final String PRPPOS  = "PRP+POS";
    static final String PRPS    = "PRP$";
    static final String RB      = "RB";
    static final String RBR     = "RBR";
    static final String RBS     = "RBS";
    static final String RP      = "RP";
    static final String SYM     = "SYM";
    static final String TO      = "TO";
    static final String UH      = "UH";
    static final String VB      = "VB";
    static final String VBD     = "VBD";
    static final String VBG     = "VBG";
    static final String VBN     = "VBN";
    static final String VBP     = "VBP";
    static final String VBZ     = "VBZ";
    static final String WDT     = "WDT";
    static final String WP      = "WP";
    static final String WPS     = "WP$";
    static final String WRB     = "WRB";
    
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
