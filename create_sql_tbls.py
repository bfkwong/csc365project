import os

def main():
    for file in os.listdir("./data"):
        tbl_name = file.split(".")[0]

        with open("./sql_data/table.sql", "a") as w_file:
            with open("./data/" + file, "r") as f_obj:
                statements = []

                f_obj.readline()
                data = f_obj.read().split("\n")

                for line in data:
                    if line != "":
                        statement = "INSERT INTO {table} VALUES ({values});".format(
                            table = tbl_name,
                            values =  line
                        )
                        statements.append(statement)

                w_file.write("\n".join(statements))








if __name__ == '__main__':
    main()
