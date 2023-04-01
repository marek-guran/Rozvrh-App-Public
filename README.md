# Rozvrh-App-Public

My personal App to track my school working with firebase.

Features:

• Firebase Realtime Database\
• Firebase Messaging Support (for receiving notifications)\
• When launched, app shows current day for schedule\
• Material You and predictive back gestures support\
• Simple to edit (Android Studio or any other IDE)\
• Easy to use and fast response times

```
For sucessfull builds you will need to create realtime database in Firebase and download google-services.json
and put it inside of app or src (dont remember it where it has to be, I had it in both folders) folder.
```

```
App is hardcoded, if you want to change content, 
you will have to use IDE for schedule and transport (Rozvrh and Domov) tab.
```

# App screenshots
<details> 
   <summary>Show</summary> 
<img src="https://user-images.githubusercontent.com/26904790/229274601-db496238-cb5d-449d-8a0f-af32485af5b1.png" width = "400px"> <img src="https://user-images.githubusercontent.com/26904790/229274621-71e3d86a-5d95-4b92-934c-2366a417bb02.png" width = "400px">
<img src="https://user-images.githubusercontent.com/26904790/229274644-476ddd70-5c4a-46ca-b554-404afe27ef2a.png" width = "400px">
<img src="https://user-images.githubusercontent.com/26904790/229274665-a46fd18f-a383-4791-8693-a0c3582d217d.png" width = "400px">
<img src="https://user-images.githubusercontent.com/26904790/229274683-2e66f37e-bed5-40a3-8e70-fb764762f822.png" width = "400px">
<img src="https://user-images.githubusercontent.com/26904790/229274799-8bfd940f-42f5-42c9-8205-e5e2604ff854.png" width = "400px">
<img src="https://user-images.githubusercontent.com/26904790/229274811-e333c0e2-9ffa-45e6-bd1a-053a6a4f6c82.png" width = "400px">
<img src="https://user-images.githubusercontent.com/26904790/229274826-1171ecf8-b521-44cd-9c05-18dcf32c503a.png" width = "400px">
<img src="https://user-images.githubusercontent.com/26904790/229274843-6442b4d1-1682-40d6-9e18-055587eaca71.png" width = "400px">
<img src="https://user-images.githubusercontent.com/26904790/229274855-face15cc-0a6f-4116-985c-ffb317175157.png" width = "400px">
   </details>

# If you dont know how to change App Icon, there are example videos how to do it

Normal Icon: https://youtu.be/bJjHgWjiAKw \
Adaptive Icon: https://youtu.be/LMQD7J2zaeM \
Monochromatic Icon (Material 3): https://youtu.be/Kje1KIwzwl0

# Example look how should Firebase look like without changing any code

**data** - your subjects with grade\
**obedy** - lunches with your remaining balance\
**zadania** - your assignments\

<details> 
   <summary>Json</summary> 
   
```json
{
  "data": {
    "L Aplikácie internetu vecí 2": {
      "gradeLetter": "",
      "subject": "☀️ Aplikácie Internetu Vecí 2"
    },
    "L Detské programovacie jazyky": {
      "gradeLetter": "",
      "subject": "☀️ Detské Programovacie Jazyky"
    },
    "L Optokomunikačné a informačné systémy 1": {
      "gradeLetter": "",
      "subject": "☀️ Optokomunikačné a Informačné Systémy 1"
    },
    "L Počítačové siete 1": {
      "gradeLetter": "",
      "subject": "☀️ Počítačové Siete 1"
    },
    "L Počítačové siete 2": {
      "gradeLetter": "",
      "subject": "☀️ Počítačové Siete 2"
    },
    "L Programovanie 3": {
      "gradeLetter": "",
      "subject": "☀️ Programovanie 3"
    },
    "L Somatický vývin dieťaťa a dorastu": {
      "gradeLetter": "",
      "subject": "☀️ Somatický Vývin Dieťaťa a Dorastu"
    },
    "L Sústredenia zo spirituality - Spiritualita dobra": {
      "gradeLetter": "",
      "subject": "☀️ Sústredenia zo Spirituality - Spiritualita Dobra"
    },
    "L Webový dizajn 2": {
      "gradeLetter": "",
      "subject": "☀️ Webový Dizajn 2"
    },
    "L Základy špeciálnej pedagogiky": {
      "gradeLetter": "",
      "subject": "☀️ Základy Špeciálnej Pedagogiky"
    },
    "Z Aplikácie internetu a vecí 1": {
      "gradeLetter": "A",
      "subject": "❄️ Aplikácie Internetu a Vecí 1"
    },
    "Z Databázové systémy": {
      "gradeLetter": "B",
      "subject": "❄️ Databázové Systémy"
    },
    "Z Internet Vecí": {
      "gradeLetter": "B",
      "subject": "❄️ Internet Vecí"
    },
    "Z Pedagogická a sociálna komunikácia": {
      "gradeLetter": "C",
      "subject": "❄️ Pedagogická a Sociálna Komunikácia"
    },
    "Z Princípy počítačov a operačné systémy 2": {
      "gradeLetter": "A",
      "subject": "❄️ Princípy PC a OS 2"
    },
    "Z Prosociálna Výchova": {
      "gradeLetter": "B",
      "subject": "❄️ Prosociálna Výchova"
    },
    "Z Sociologické aspekty edukácie": {
      "gradeLetter": "C",
      "subject": "❄️ Sociologické Aspekty Edukácie"
    },
    "Z Stáž A": {
      "gradeLetter": "A",
      "subject": "❄️ Stáž A"
    },
    "Z Teoretické základy informatiky": {
      "gradeLetter": "D",
      "subject": "❄️ Teoretické Základy Informatiky"
    },
    "Z Vizuálna kultúra 1": {
      "gradeLetter": "A",
      "subject": "❄️ Vizuálna Kultúra 1"
    },
    "Z Webový dizajn 1": {
      "gradeLetter": "A",
      "subject": "❄️ Webový Dizajn 1"
    },
    "Z Základné témy biblie": {
      "gradeLetter": "ABSOL",
      "subject": "❄️ Základné Témy Biblie"
    }
  },
  "obedy": {
    "obed": {
      "1 Pondelok": "Pondelok: Neobjednané",
      "2 Utorok": "Utorok: Neobjednané",
      "3 Streda": "Streda: Neobjednané",
      "4 Stvrtok": "Štvrtok: Neobjednané"
    },
    "stavKonta": "23.84 €"
  },
  "zadania": {
    "Detske": {
      "date": "🕐 Do konca semestra",
      "grade_details": "🎮 Hra",
      "subject_name": "Detské Programovacie Jazyky",
      "teacher": "👤 Jacková"
    },
    "IoT": {
      "date": "🕐 Do konca semestra",
      "grade_details": "🧑‍💻 Teplomer - skupinový projekt",
      "subject_name": "Aplikácie Internetu Vecí 2",
      "teacher": "👤 Pillár"
    },
    "WD2": {
      "date": "🕐 Do konca semestra",
      "grade_details": "🌐 Webstránka",
      "subject_name": "Webový Dizajn 2",
      "teacher": "👤 Pillár"
    }
  }
}
```
   </details>
