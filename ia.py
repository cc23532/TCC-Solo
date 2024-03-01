def execute_code(code):
   try:
       exec(code)
   except Exception as e:
       print("Ocorreu um erro durante a execução do código:")
       print(e)


if __name__ == "__main__":
   print("Bem-vindo à IA de execução de código Python!")
   print("Digite seu código abaixo (pressione Enter duas vezes para executar):")
  
   user_code = ""
   while True:
       line = input()
       if line.strip() == "":
           break
       user_code += line + "\n"
  
   print("Executando seu código...")
   execute_code(user_code)
