from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider

cloud_config = {
    'secure_connect_bundle': '/path/to/secure-connect-dbname.zip'
}
auth_provider = PlainTextAuthProvider(username='cassandra', password='cassandra')
cluster = Cluster(auth_provider=auth_provider)
session = cluster.connect()
session.set_keyspace('ebank_data')
# or you can do this instead
session.execute('USE ebank_data')
rows = session.execute('SELECT  emp_id, emp_city, emp_name, emp_phone, emp_sal FROM emp')
for user_row in rows:
    print (user_row.emp_id, user_row.emp_city, user_row.emp_name, user_row.emp_phone, user_row.emp_sal) 