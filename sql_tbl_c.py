import os

def main():
    with open("./sql_data/table.sql", "w") as w_file:
        final_statements = ""

        for file in sorted(os.listdir("./data")):
            print(file)
            tbl_name = file.split(".")[0].split("_")[1]

            with open("./data/" + file, "r") as f_obj:
                f_obj.readline()
                data = f_obj.read().split("\n")

                for line in data:
                    if line != "":
                        statement = "INSERT INTO {table} VALUES ({values});\n".format(
                            table = tbl_name,
                            values =  line
                        )
                        final_statements += statement

        w_file.write(final_statements)

    return


if __name__ == '__main__':
    main()
