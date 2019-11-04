package com.polytech.amusees.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polytech.amusees.database.UserDao
import com.polytech.amusees.model.User
import kotlinx.coroutines.*

//liste des pays reconnus par l'ONU
enum class Countries(val nameCountry: String) {
    AFG("Afghanistan"),
    ADS("Afrique du Sud"),
    ALB("Albanie"),
    ALG("Algérie"),
    ALL("Allemagne"),
    AND("Andorre"),
    ANG("Angola"),
    AEB("Antigua-et-Barbuda"),
    ARB("Arabie Saoudite"),
    ARG("Argentine"),
    ARM("Arménie"),
    AUS("Australie"),
    AUT("Autriche"),
    AZE("Azerbaïdjan"),
    BAHA("Bahamas"),
    BAHR("Bahreïn"),
    BAN("Bangladesh"),
    BAR("Barbade"),
    BELG("Belgique"),
    BELI("Belize"),
    BEN("Bénin"),
    BHO("Bhoutan"),
    BIE("Biélorussie"),
    BIR("Birmanie"),
    BOL("Bolivie"),
    BOS("Bosnie-Herzégovine"),
    BOT("Botswana"),
    BRE("Brésil"),
    BRU("Brunei"),
    BUL("Bulgarie"),
    BURK("Burkina Faso"),
    BURU("Burundi"),
    CAMB("Cambodge"),
    CAME("Cameroun"),
    CAN("Canada"),
    CAP("Cap-Vert"),
    RCA("République centrafricaine"),
    CHIL("Chili"),
    CHIN("Chine"),
    CHY("Chypre"),
    COL("Colombie"),
    COM("Comores"),
    CON("République du Congo"),
    RDC("République démocratique du Congo"),
    COO("Îles Cook"),
    CORN("Corée du Nord"),
    CORS("Corée du Sud"),
    COS("Costa Rica"),
    COT("Côte d’Ivoire"),
    CRO("Croatie"),
    CUB("Cuba"),
    DAN("Danemark"),
    DJI("Djibouti"),
    RDOM("République dominicaine"),
    DOM("Dominique"),
    EGY("Égypte"),
    EMI("Émirats arabes unis"),
    EQU("Équateur"),
    ERY("Érythrée"),
    ESP("Espagne"),
    EST("Estonie"),
    USA("États-Unis"),
    ETH("Éthiopie"),
    FID("Fidji"),
    FIN("Finlande"),
    FRA("France"),
    GAB("Gabon"),
    GAM("Gambie"),
    GEO("Géorgie"),
    GHA("Ghana"),
    GREC("Grèce"),
    GREN("Grenade"),
    GUA("Guatemala"),
    GUI("Guinée"),
    GUIB("Guinée-Bissau"),
    GUIE("Guinée équatoriale"),
    GUY("Guyana"),
    HAI("Haïti"),
    HOND("Honduras"),
    HONG("Hongrie"),
    INDE("Inde"),
    INDO("Indonésie"),
    IRAK("Irak"),
    IRAN("Iran"),
    IRL("Irlande"),
    ISL("Islande"),
    ISR("Israël"),
    ITA("Italie"),
    JAM("Jamaïque"),
    JAP("Japon"),
    JOR("Jordanie"),
    KAZ("Kazakhstan"),
    KEN("Kenya"),
    KIRG("Kirghizistan"),
    KIRI("Kiribati"),
    KOW("Koweït"),
    LAO("Laos"),
    LES("Lesotho"),
    LET("Lettonie"),
    LIBA("Liban"),
    LIBE("Liberia"),
    LIBY("Libye"),
    LIE("Liechtenstein"),
    LIT("Lituanie"),
    LUX("Luxembourg"),
    MAC("Macédoine du Nord"),
    MAD("Madagascar"),
    MALAI("Malaisie"),
    MALAW("Malawi"),
    MALD("Maldives"),
    MALI("Mali"),
    MALT("Malte"),
    MARO("Maroc"),
    MARS("Îles Marshall"),
    MAURIC("Maurice"),
    MAURIT("Mauritanie"),
    MEX("Mexique"),
    MIC("Micronésie"),
    MOL("Moldavie"),
    MON("Monaco"),
    MONG("Mongolie"),
    MONT("Monténégro"),
    MOZ("Mozambique"),
    NAM("Namibie"),
    NAU("Nauru"),
    NEP("Népal"),
    NIC("Nicaragua"),
    NIG("Niger"),
    NIGI("Nigeria"),
    NIU("Niue"),
    NOR("Norvège"),
    NOU("Nouvelle-Zélande"),
    OMA("Oman"),
    OUG("Ouganda"),
    OUZ("Ouzbékistan"),
    PAK("Pakistan"),
    PALA("Palaos"),
    PALE("Palestine"),
    PAN("Panamá"),
    PNG("Papouasie-Nouvelle-Guinée"),
    PAR("Paraguay"),
    PAY("Pays-Bas"),
    PER("Pérou"),
    PHI("Philippines"),
    POL("Pologne"),
    POR("Portugal"),
    QAT("Qatar"),
    ROU("Roumanie"),
    ROY("Royaume-Uni"),
    RUS("Russie"),
    RWA("Rwanda"),
    SCN("Saint-Christophe-et-Niévès"),
    SMA("Saint-Marin"),
    SVG("Saint-Vincent-et-les Grenadines"),
    SLU("Sainte-Lucie"),
    SALO("Îles Salomon"),
    SALV("Salvador"),
    SAM("Samoa"),
    SAO("São Tomé-et-Principe"),
    SEN("Sénégal"),
    SER("Serbie"),
    SEY("Seychelles"),
    SIE("Sierra Leone"),
    SIN("Singapour"),
    SLOVA("Slovaquie"),
    SLOVE("Slovénie"),
    SOM("Somalie"),
    SOU("Soudan"),
    SOUS("Soudan du Sud"),
    SRI("Sri Lanka"),
    SUE("Suède"),
    SUI("Suisse"),
    SUR("Suriname"),
    SWA("Swaziland"),
    SYR("Syrie"),
    TAD("Tadjikistan"),
    TAN("Tanzanie"),
    TCHA("Tchad"),
    TCHE("République tchèque"),
    THA("Thaïlande"),
    TIM("Timor oriental"),
    TOG("Togo"),
    TON("Tonga"),
    TRI("Trinité-et-Tobago"),
    TUN("Tunisie"),
    TURK("Turkménistan"),
    TURQ("Turquie"),
    TUV("Tuvalu"),
    UKR("Ukraine"),
    URU("Uruguay"),
    VAN("Vanuatu"),
    VAT("Vatican"),
    VEN("Venezuela"),
    VIE("Viêt Nam"),
    YEM("Yémen"),
    ZAM("Zambie"),
    ZIM("Zimbabwe");

    override fun toString(): String {
        return nameCountry
    }

    //TODO à revoir / déplacer
    fun countryNames() : Array<String?> {
        var names: ArrayList<String> = arrayListOf()
        for (country in Countries.values()) {
            names.add(country.toString())
        }

        val arrayNames = arrayOfNulls<String>(names.size)
        names.toArray(arrayNames)

        return arrayNames
    }
}

class RegisterLocationViewModel(
    val database: UserDao,
    application: Application,
    private val myUser: User
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        Log.i("RegisterLocationVM", "created")
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = myUser
        }
    }

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = null
    }

    //goto account
    private val _navigateToRegisterAccountFragment = MutableLiveData<User>()

    val navigateToRegisterAccountFragment: LiveData<User>
        get() = _navigateToRegisterAccountFragment


    fun onValidateLocation() {
        Log.i("200","Click location")
        uiScope.launch {
            val user = user.value ?: return@launch

            if(user.adress.isNullOrEmpty()) { //TODO vérif format ?
                _alert.value = "Veuillez saisir votre adresse"
                return@launch
            }

            if(user.city.isNullOrEmpty()) {//test si bien que des lettres (ou tirets ...)
                _alert.value = "Veuillez saisir votre ville"
                return@launch
            }

            if(user.country.isNullOrEmpty()) {
                _alert.value = "Veuillez sélectionner votre pays"
                return@launch
            }

            _navigateToRegisterAccountFragment.value = user
        }
    }

    fun onCountrySelected(position: Int) {
        user.value?.country = Countries.values()[position].toString()
    }

    fun doneNavigating() {
        _navigateToRegisterAccountFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("RegisterLocationVM", "destroyed")
        viewModelJob.cancel()
    }
}