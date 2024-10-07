from pymongo import MongoClient

# Conectar ao MongoDB
client = MongoClient("mongodb+srv://cc23532:#Math080501@carle2ti.v365p.mongodb.net/?retryWrites=true&w=majority&appName=carle2ti")
db = client['carle2ti']
food_data_ibge = db['food_data_ibge']

# Inserir dados -- TESTE INICIAL
for documento in food_data_ibge.find():
    print(documento)

# Inserir múltiplos dados
# food_data_ibge.insert_many([
  #  {"nome": "Maria Oliveira", "email": "maria.oliveira@example.com"},
   # {"nome": "Carlos Pereira", "email": "carlos.pereira@example.com"}
#])
