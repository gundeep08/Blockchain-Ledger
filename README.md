# blockchain-ledger
It's a project that uses blockchain ledger system to process transactions.

Blockchain technology allows the creation of distributed trust networks that promise to disrupt
many existing industries where trust has traditionally been provided through intermediaries (e.g.,
Banks, Insurance Companies).

This is the implementation of a blockchain Ledger Service.  The Ledger Service
manages the transactions, accounts, and blocks that make up the Blockchain.Users submit
transactions that, once validated, are added to a block.  As Blocks fill up with Transactions,
Account balances are updated, and the Blocks are added to the Ledger.  Once committed to the
Ledger, a Block, and the contained Transactions and Account balances are immutable.  To
ensure the immutability of the blocks, the blocks are chained together by including the hash of
the previous block as a field in each new block.  The blockchain can be validated at any time by
recomputing the hashes of each block and comparing the result with the hash stored in the next
block.

Blockchain Properties
● Blockchain is a public ledger
● A sequence of transactions organized in blocks, guaranteeing three fundamental
properties:
○ Everybody can read every block, so the blocks become common knowledge
○ Everybody can write a transaction in a future block
○ No one can alter the transactions in a block or the order of the blocks
Requirements
This section defines the requirements for the Ledger System.
Transaction Processing
1. The Ledger Service should accept transactions for inclusion in the blockchain.
2. Transactions should only be accepted if the paying and receiving addresses are linked to
valid accounts.
3. Transactions should only be accepted (committed to the block) if the paying account has
a sufficient balance to cover the amount and associated transaction fee.
4. Once accepted, a transaction will be assigned a unique transaction id and added to a
Block.
1
Ledger Service Design Document
CSCI E-97 Assignment 1 8/29/2022
5. All accepted transactions require a fee of at least 10 units from the payer’s account.  The
fees are transferred to the master account.
Accounts
1. The Ledger will manage a list of accounts.
2. When the blockchain is initialized, a master account is created that contains all currency,
2147483647 units.
3. Each new account is assigned a unique address.
4. All accounts have a balance that can be credited or debited through transactions.
5. The minimum balance for accounts is 0, and the maximum account balance is
2147483647 (i.e., Integer.MAX_VALUE).
6. The initial account balance for a new account is 0.
Blocks
1. Blocks are collections of transactions.
2. Each block contains exactly 10 transactions.
3. Each block contains a header that includes the hash of the previous block using the
Sha-256 algorithm to compute the hash based on a Merkle tree of the contained
transactions.  (https://en.wikipedia.org/wiki/Merkle_tree)
4. Each block contains a map of all accounts and their balances which reflect any
transactions within the block.
Example subsequence of blocks showing the Merkle tree for block 11.
