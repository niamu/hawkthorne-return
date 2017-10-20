(ns hawkthorne.room)

(def ^:private course-adjectives
  ["Queer Studies of" "Modern" "Communication" "History of" "A Fistful of"
   "Paranormal" "The Art of" "Emotional Consequences of" "Social"
   "Contemporary" "Early" "Survey of" "Introduction to" "Uncontrollable"
   "Interior" "Conventions of" "Celebrity" "Pascal's Triangle Revisited"
   "Romantic" "Early 21st Century" "English as" "Competitive"
   "Aerodynamics of" "Ancient" "Asian" "Football and" "Investigative"
   "Underwater" "Conspiracy Theories" "Origins of" "Simplified" "Laws of"
   "Mixology" "Principles of" "Beginner" "Analysis of" "Psychology of"
   "Physical" "Theoretical" "Ladders" "Epidemiology" "Interpretive"
   "Virtual Systems Analysis of" "Cork-Based" "Accounting for" "Documentary"
   "App Development of" "Economics of" "Herstory of" "Intermediate"
   "Advanced Introduction to" "Sandwiched" "The First Chang Dynasty" "Wedding"
   "Geothermal" "Urban Matrimony" "A Critical Analysis of" "Messianic Myths"
   "Pillows and Blankets of" "Nominal" "Regional" "Alternative" "Debate"
   "The Politics of" "Heroic" "Remedial" "Digital Exploration of" "Educational"
   "Anthropology" "Biology of" "Cooperative" "Abed's Uncontrollable" "Basic"
   "Critical" "Intergluteal" "Grifting" "Intro to" "Crisis Room"
   "Eastern European" "Studies in" "Spanish" "The Psychology of" "Advanced"
   "Applied" "Comparative" "Custody Law" "Foosball and" "The Science of"
   "Bondage" "Topics in" "Felt-Based" "Curriculum" "Environmental"
   "Advanced Advanced" "Geography of" "Course Listing" "Home"
   "VCR Maintenance and" "Beta" "Paradigms of"])

(def ^:private course-nouns
  ["Romanticism" "Email Security" "Design" "Indoor Camping"
   "Robotics and Party Rights" "Illusion" "Nicolas Cage" "Human Memory"
   "Human Anatomy" "Psychology" "Marine Biology" "Espionage"
   "Anthropology and Culinary Arts" "Vampire Mythology" "Studyology"
   "Holiday Music" "Learning!" "PA Announcement" "Paintballs"
   "Dungeons & Dragons" "Breath Holding" "Population Studies" "Italian Wines"
   "American Poultry" "Gay" "Human Sexuality" "Class" "RV Repair" "Education"
   "Lawyers" "Conspiracy Theories" "Expressionism" "Impressionists" "Science"
   "Basket Weaving" "Ascertainment" "Waxing" "Pharmacology"
   "Nocturnal Vigilantism" "Documentary Filmmaking" "Gender" "Water Skiing"
   "Biology" "Calligraphy" "Videography" "Intermediate" "Surrogacy"
   "Lupine Urology" "Lawnmower Maintenance"
   "Horror Fiction in Seven Spooky Steps" "Numismatics" "Sandwiches"
   "Recycled Cinema" "Ecology" "Economics" "Studies" "Palmistry" "Letting Go"
   "Nutrition" "History of the German Invasion" "People" "Something"
   "Filmmaking: Redux" "Knots" "Maths" "Discourse" "Broadcast Television"
   "Decorum" "U.S. History" "Networking" "Certification" "Rocket Science"
   "Theory" "Christmas" "Indoor Spelunking" "Political Science" "Baby Talk"
   "Religion" "Powerpoint Mastery" "Polygraphy" "Film" "Chinese" "Genealogy"
   "Film Studies" "Fencing" "Dog-Grooming" "Finality" "Media Literacy"
   "Space and Time" "Condiments" "Escapism" "Tap Dance" "Warfare" "Statistics"
   "Story" "Grifting" "Language" "Wine Tasting" "Sailing" "Feminism"
   "Male Sexuality" "Ice Cream" "Criminal Law" "Pottery" "Spanish" "Publishing"
   "Billiards" "Beekeeping" "Origins" "Global Conflict" "Safety Features"
   "Teaching" "Diplomacy" "Joke Writing" "Interior Design" "Parentage"
   "Journalism" "Modern Dance" "Escapism in Familial Relations" "Phys. Ed."
   "Postnatal Care" "Modern Movement" "Dance" "Arts & Crafts" "Beets"
   "Basics"])

(defn random-course
  []
  (format "%s %s" (rand-nth course-adjectives) (rand-nth course-nouns)))
