package me.slavita.construction.utils.language

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*

enum class EnumLang(val locale: String, val map: MutableMap<String, String>) {
    AF_ZA("af_za", hashMapOf()), AR_SA("ar_sa", hashMapOf()), AST_ES(
        "ast_es",
        hashMapOf()
    ),
    AZ_AZ("az_az", hashMapOf()), BE_BY("be_by", hashMapOf()), BG_BG(
        "bg_bg",
        hashMapOf()
    ),
    BR_FR("br_fr", hashMapOf()), CA_ES("ca_es", hashMapOf()), CS_CZ(
        "cs_cz",
        hashMapOf()
    ),
    CY_GB("cy_gb", hashMapOf()), DA_DK("da_dk", hashMapOf()), DE_AT(
        "de_at",
        hashMapOf()
    ),
    DE_DE("de_de", hashMapOf()), EL_GR("el_gr", hashMapOf()), EN_AU(
        "en_au",
        hashMapOf()
    ),
    EN_CA("en_ca", hashMapOf()), EN_GB("en_gb", hashMapOf()), EN_NZ(
        "en_nz",
        hashMapOf()
    ),
    EN_PT("en_pt", hashMapOf()), EN_UD("en_ud", hashMapOf()), EN_US(
        "en_us",
        hashMapOf()
    ),
    EO_UY("eo_uy", hashMapOf()), ES_AR("es_ar", hashMapOf()), ES_ES(
        "es_es",
        hashMapOf()
    ),
    ES_MX("es_mx", hashMapOf()), ES_UY("es_uy", hashMapOf()), ES_VE(
        "es_ve",
        hashMapOf()
    ),
    ET_EE("et_ee", hashMapOf()), EU_ES("eu_es", hashMapOf()), FA_IR(
        "fa_ir",
        hashMapOf()
    ),
    FIL_PH("fil_ph", hashMapOf()), FI_FI("fi_fi", hashMapOf()), FO_FO(
        "fo_fo",
        hashMapOf()
    ),
    FR_CA("fr_ca", hashMapOf()), FR_FR("fr_fr", hashMapOf()), FY_NL(
        "fy_nl",
        hashMapOf()
    ),
    GA_IE("ga_ie", hashMapOf()), GD_GB("gd_gb", hashMapOf()), GL_ES(
        "gl_es",
        hashMapOf()
    ),
    GV_IM("gv_im", hashMapOf()), HAW_US("haw_us", hashMapOf()), HE_IL(
        "he_il",
        hashMapOf()
    ),
    HI_IN("hi_in", hashMapOf()), HR_HR("hr_hr", hashMapOf()), HU_HU(
        "hu_hu",
        hashMapOf()
    ),
    HY_AM("hy_am", hashMapOf()), ID_ID("id_id", hashMapOf()), IO_IDO(
        "io_ido",
        hashMapOf()
    ),
    IS_IS("is_is", hashMapOf()), IT_IT("it_it", hashMapOf()), JA_JP(
        "ja_jp",
        hashMapOf()
    ),
    JBO_EN("jbo_en", hashMapOf()), KA_GE("ka_ge", hashMapOf()), KO_KR(
        "ko_kr",
        hashMapOf()
    ),
    KSH_DE("ksh_de", hashMapOf()), KW_GB("kw_gb", hashMapOf()), LA_LA(
        "la_la",
        hashMapOf()
    ),
    LB_LU("lb_lu", hashMapOf()), LI_LI("li_li", hashMapOf()), LOL_US(
        "lol_us",
        hashMapOf()
    ),
    LT_LT("lt_lt", hashMapOf()), LV_LV("lv_lv", hashMapOf()), MI_NZ(
        "mi_nz",
        hashMapOf()
    ),
    MK_MK("mk_mk", hashMapOf()), MN_MN("mn_mn", hashMapOf()), MS_MY(
        "ms_my",
        hashMapOf()
    ),
    MT_MT("mt_mt", hashMapOf()), NDS_DE("nds_de", hashMapOf()), NL_NL(
        "nl_nl",
        hashMapOf()
    ),
    NN_NO("nn_no", hashMapOf()), NO_NO("no_no", hashMapOf()), OC_FR(
        "oc_fr",
        hashMapOf()
    ),
    PL_PL("pl_pl", hashMapOf()), PT_BR("pt_br", hashMapOf()), PT_PT(
        "pt_pt",
        hashMapOf()
    ),
    QYA_AA("qya_aa", hashMapOf()), RO_RO("ro_ro", hashMapOf()), RU_RU(
        "ru_ru",
        hashMapOf()
    ),
    SE_NO("se_no", hashMapOf()), SK_SK("sk_sk", hashMapOf()), SL_SI(
        "sl_si",
        hashMapOf()
    ),
    SO_SO("so_so", hashMapOf()), SQ_AL("sq_al", hashMapOf()), SR_SP(
        "sr_sp",
        hashMapOf()
    ),
    SV_SE("sv_se", hashMapOf()), SWG_DE("swg_de", hashMapOf()), TH_TH(
        "th_th",
        hashMapOf()
    ),
    TLH_AA("tlh_aa", hashMapOf()), TR_TR("tr_tr", hashMapOf()), TZL_TZL(
        "tzl_tzl",
        hashMapOf()
    ),
    UK_UA("uk_ua", hashMapOf()), VAL_ES("val_es", hashMapOf()), VI_VN(
        "vi_vn",
        hashMapOf()
    ),
    ZH_CN("zh_cn", hashMapOf()), ZH_TW("zh_tw", hashMapOf());

    companion object {
        private val lookup: MutableMap<String, EnumLang> = HashMap()

        init {
            for (lang in EnumSet.allOf(EnumLang::class.java)) lookup[lang.locale] = lang
        }

        operator fun get(locale: String): EnumLang {
            val result = lookup[locale]
            return result ?: EN_US
        }

        fun init() {
            for (enumLang in values()) {
                try {
                    readFile(
                        enumLang,
                        BufferedReader(
                            InputStreamReader(
                                EnumLang::class.java.getResourceAsStream("/lang/" + enumLang.locale + ".lang")!!,
                                StandardCharsets.UTF_8
                            )
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun clean() {
            for (enumLang in values()) {
                enumLang.map.clear()
            }
        }

        private fun readFile(enumLang: EnumLang, reader: BufferedReader) {
            var temp: String?
            var tempStringArr: Array<String>
            try {
                temp = reader.readLine()
                while (temp != null) {
                    if (temp.startsWith("#")) continue
                    if (temp.contains("=")) {
                        tempStringArr = temp.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        enumLang.map[tempStringArr[0]] = if (tempStringArr.size > 1) tempStringArr[1] else ""
                    }
                    temp = reader.readLine()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                reader.close()
            }
        }
    }
}